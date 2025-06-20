/**
 * Copyright (c) 2010-2025 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.ferroamp.internal.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.ferroamp.internal.FerroampBindingConstants;
import org.openhab.binding.ferroamp.internal.api.FerroampMqttCommunication;
import org.openhab.binding.ferroamp.internal.config.ChannelMapping;
import org.openhab.binding.ferroamp.internal.config.FerroampConfiguration;
import org.openhab.core.io.transport.mqtt.MqttBrokerConnection;
import org.openhab.core.io.transport.mqtt.MqttMessageSubscriber;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link FerroampHandler} is responsible for handling the values sent to and from the binding.
 *
 * @author Örjan Backsell - Initial contribution
 *
 */

@NonNullByDefault
public class FerroampHandler extends BaseThingHandler implements MqttMessageSubscriber {
    private final static Logger logger = LoggerFactory.getLogger(FerroampHandler.class);
    private @Nullable static MqttBrokerConnection ferroampConnection;
    FerroampMqttCommunication ferroampMqttCommunication = new FerroampMqttCommunication();
    FerroampConfiguration ferroampConfig = new FerroampConfiguration();

    private static List<ChannelMapping> channelConfigEhub = new ArrayList<>();
    private static List<ChannelMapping> channelConfigEso = new ArrayList<>();
    private static List<ChannelMapping> channelConfigEsm = new ArrayList<>();

    private @Nullable ScheduledFuture<?> ferroampPoller;

    public FerroampHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        String transId = UUID.randomUUID().toString();
        String valueConfiguration = command.toString();
        if (FerroampBindingConstants.CHANNEL_REQUESTCHARGE.equals(channelUID.getId())) {
            String requestCmdJsonCharge = "{\"" + "transId" + "\":\"" + transId
                    + "\",\"cmd\":{\"name\":\"charge\",\"arg\":\"" + valueConfiguration + "\"}}";
            FerroampMqttCommunication.sendPublishedTopic(requestCmdJsonCharge, ferroampConfig);
        }
        if (FerroampBindingConstants.CHANNEL_REQUESTDISCHARGE.equals(channelUID.getId())) {
            String requestCmdJsonDisCharge = "{\"" + "transId" + "\":\"" + transId
                    + "\",\"cmd\":{\"name\":\"discharge\",\"arg\":\"" + valueConfiguration + "\"}}";
            FerroampMqttCommunication.sendPublishedTopic(requestCmdJsonDisCharge, ferroampConfig);
        }
        if (FerroampBindingConstants.CHANNEL_AUTO.equals(channelUID.getId())) {
            String requestCmdJsonAuto = "{\"" + "transId" + "\":\"" + transId + "\",\"cmd\":{\"name\":\"auto\"}}";
            FerroampMqttCommunication.sendPublishedTopic(requestCmdJsonAuto, ferroampConfig);
        }
    }

    @Override
    public void initialize() {
        // Set configuration parameters
        ferroampConfig = getConfigAs(FerroampConfiguration.class);

        // Set channel configuration parameters
        channelConfigEhub = ChannelMapping.getChannelConfigurationEhub();
        channelConfigEso = ChannelMapping.getChannelConfigurationEso();
        channelConfigEsm = ChannelMapping.getChannelConfigurationEsm();

        if (!ferroampConfig.hostName.isBlank() || !ferroampConfig.password.isBlank()
                || !ferroampConfig.userName.isBlank()) {
            final MqttBrokerConnection ferroampConnection = new MqttBrokerConnection(ferroampConfig.hostName,
                    FerroampBindingConstants.BROKER_PORT, false, false, ferroampConfig.userName);
            updateStatus(ThingStatus.UNKNOWN);
            ferroampPoller = scheduler.scheduleWithFixedDelay(this::pollTask, 60, ferroampConfig.refreshInterval,
                    TimeUnit.SECONDS);
            this.setFerroampConnection(ferroampConnection);
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
        }
    }

    @Override
    public void dispose() {
        final ScheduledFuture<?> localPoller = ferroampPoller;

        if (ferroampConnection != null) {
            localPoller.cancel(true);
        }

        if (localPoller != null && !localPoller.isCancelled()) {
            ferroampConnection.stop();
            ferroampPoller = null;
        }
    }

    private void pollTask() {
        try {
            startMqttConnection(getConfigAs(FerroampConfiguration.class));
        } catch (InterruptedException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
            logger.debug("Connection interrupted");
            return;
        }

        MqttBrokerConnection ferroampConnection = FerroampHandler.ferroampConnection;
        if (ferroampConnection == null || ferroampConnection.connectionState().toString().equals("DISCONNECTED")) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
            logger.debug("No connection to MqttBroker");
        } else if (ferroampConnection.connectionState().toString().equals("CONNECTED")) {
            updateStatus(ThingStatus.ONLINE);
            try {
                channelUpdate();
            } catch (RuntimeException scheduleWithFixedDelayException) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                        scheduleWithFixedDelayException.getClass().getName() + ":"
                                + scheduleWithFixedDelayException.getMessage());
            }
        }
    }

    private void startMqttConnection(FerroampConfiguration ferroampConfig) throws InterruptedException {
        MqttBrokerConnection localSubscribeConnection = FerroampHandler.getFerroampConnection();
        Objects.requireNonNull(localSubscribeConnection,
                "MqttBrokerConnection localSubscribeConnection cannot be null");
        localSubscribeConnection.start();
        localSubscribeConnection.setCredentials(ferroampConfig.userName, ferroampConfig.password);
        ferroampMqttCommunication.getSubscribedTopic(FerroampBindingConstants.EHUB_TOPIC, ferroampConfig);
        ferroampMqttCommunication.getSubscribedTopic(FerroampBindingConstants.SSO_TOPIC, ferroampConfig);
        ferroampMqttCommunication.getSubscribedTopic(FerroampBindingConstants.ESO_TOPIC, ferroampConfig);
        ferroampMqttCommunication.getSubscribedTopic(FerroampBindingConstants.ESM_TOPIC, ferroampConfig);
    }

    private void channelUpdate() {
        String[] ehubUpdateChannels;
        ehubUpdateChannels = FerroampMqttCommunication.getEhubChannelUpdateValues();
        if (ehubUpdateChannels.length > 0) {
            int channelValuesCounterEhub = 0;
            for (ChannelMapping cConfig : channelConfigEhub) {
                String ehubChannel = cConfig.id;
                State ehubState = StringType.valueOf(ehubUpdateChannels[channelValuesCounterEhub]);
                updateState(ehubChannel, ehubState);
                channelValuesCounterEhub++;
            }
        }

        int ssoNumber = 4; // Number of SSO's
        for (int ssoIndex = 0; ssoIndex < ssoNumber; ssoNumber++) {
            Map<String, @Nullable String> keyValueMap = ferroampMqttCommunication.getSsoChannelUpdateValues(ssoIndex);
            for (ChannelMapping mapping : ChannelMapping.getSSOChannelMapping()) {
                State newState = StringType.valueOf(keyValueMap.get(mapping.jsonKey));
                updateState("sso-" + ssoIndex + 1 + "#" + mapping.id, newState);
            }
        }

        String[] esoUpdateChannels = new String[11];
        esoUpdateChannels = FerroampMqttCommunication.getEsoChannelUpdateValues();
        if (esoUpdateChannels.length > 0) {
            int channelValuesCounterEso = 0;
            if (esoUpdateChannels.length <= 9) {
                for (ChannelMapping cConfig : channelConfigEso) {
                    String esoChannel = cConfig.id;
                    State esoState = StringType.valueOf(esoUpdateChannels[channelValuesCounterEso]);
                    updateState(esoChannel, esoState);
                    channelValuesCounterEso++;
                }
            }
        }

        String[] esmUpdateChannels = new String[7];
        esmUpdateChannels = FerroampMqttCommunication.getEsmChannelUpdateValues();
        if (esmUpdateChannels.length > 0) {
            int channelValuesCounterEsm = 0;
            if (esmUpdateChannels.length <= 9) {
                for (ChannelMapping cConfig : channelConfigEsm) {
                    String esmChannel = cConfig.id;
                    State esmState = StringType.valueOf(esmUpdateChannels[channelValuesCounterEsm]);
                    updateState(esmChannel, esmState);
                    channelValuesCounterEsm++;
                }
            }
        }
    }

    // Capture actual Json-topic message
    @Override
    public void processMessage(String topic, byte[] payload) {
    }

    public @Nullable static MqttBrokerConnection getFerroampConnection() {
        try {
            return ferroampConnection;
        } catch (Exception e) {
            logger.debug("Connection to MqttBroker disturbed during startup of MqttConnection");
        }
        return ferroampConnection;
    }

    public void setFerroampConnection(@Nullable MqttBrokerConnection ferroampConnection) {
        FerroampHandler.ferroampConnection = ferroampConnection;
    }
}
