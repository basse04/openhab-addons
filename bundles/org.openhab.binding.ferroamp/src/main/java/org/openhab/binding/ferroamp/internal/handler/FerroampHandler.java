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
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.ferroamp.internal.FerroampBindingConstants;
import org.openhab.binding.ferroamp.internal.api.FerroampMqttCommunication;
import org.openhab.binding.ferroamp.internal.config.ChannelMapping;
import org.openhab.binding.ferroamp.internal.config.FerroampConfiguration;
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
public class FerroampHandler extends BaseThingHandler {
    private final static Logger logger = LoggerFactory.getLogger(FerroampHandler.class);
    private static List<ChannelMapping> channelConfigEhub = new ArrayList<>();
    private @Nullable FerroampMqttCommunication ferroampMqttCommunication;
    private FerroampConfiguration ferroampConfig = new FerroampConfiguration();

    private @Nullable ScheduledFuture<?> pollTask;

    public FerroampHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        FerroampMqttCommunication ferroampMqttCommunication = this.ferroampMqttCommunication;
        if (ferroampMqttCommunication == null) {
            logger.warn("FerroampMqttCommunication is not initialized");
            return;
        }

        String commandType = "";

        // TODO this might be better modeled into one channel with 3 values/modes
        switch (channelUID.getId()) {
            case FerroampBindingConstants.CHANNEL_REQUEST_CHARGE:
                commandType = "charge";
                break;
            case FerroampBindingConstants.CHANNEL_REQUEST_DISCHARGE:
                commandType = "discharge";
                break;
            case FerroampBindingConstants.CHANNEL_REQUEST_AUTO:
                commandType = "auto";
                break;
            default:
                logger.warn("Unknown channel: {}", channelUID.getId());
                return;
        }

        String requestCommand = "{\"" + "transId" + "\":\"" + UUID.randomUUID().toString() + "\",\"cmd\":{\"name\":\""
                + commandType + "\",\"arg\":\"" + command.toString() + "\"}}";
        ferroampMqttCommunication.sendPublishedTopic(requestCommand, ferroampConfig);
    }

    @Override
    public void initialize() {
        // Set configuration parameters
        ferroampConfig = getConfigAs(FerroampConfiguration.class);

        // Set channel configuration parameters
        channelConfigEhub = ChannelMapping.getChannelConfigurationEhub();

        if (ferroampConfig.hostName.isBlank() || ferroampConfig.password.isBlank()
                || ferroampConfig.userName.isBlank()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
            return;
        }
        updateStatus(ThingStatus.UNKNOWN);

        //TODO handle the case where user or pass is not valid
        ferroampMqttCommunication = new FerroampMqttCommunication(ferroampConfig.hostName, ferroampConfig.password,
                ferroampConfig.hostName, FerroampBindingConstants.BROKER_PORT);

        pollTask = scheduler.scheduleWithFixedDelay(this::pollTask, 60, ferroampConfig.refreshInterval,
                TimeUnit.SECONDS);
    }

    @Override
    public void dispose() {
        final ScheduledFuture<?> pollTask = this.pollTask;
        if (pollTask != null) {
            pollTask.cancel(true);
            this.pollTask = null;
        }
    }

    private void pollTask() {
        FerroampMqttCommunication ferroampConnection = this.ferroampMqttCommunication;
        if (ferroampConnection == null || !ferroampConnection.isConnected()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR);
            logger.debug("Ferroamp MqttBroker is not connected");
            return;
        }

        updateStatus(ThingStatus.ONLINE);

        //TODO, MQTT is event driven, so it is a little weird to poll
        try {
            channelUpdate();
        } catch (RuntimeException re) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    re.getClass().getName() + ":" + re.getMessage());
        }
    }

    private void channelUpdate() {
        FerroampMqttCommunication ferroampMqttCommunication = this.ferroampMqttCommunication;
        if (ferroampMqttCommunication == null) {
            logger.warn("FerroampMqttCommunication is not initialized");
            return;
        }

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
            for (ChannelMapping mapping : ChannelMapping.getSSOMapping()) {
                State newState = StringType.valueOf(keyValueMap.get(mapping.jsonKey));
                updateState("sso-" + ssoIndex + 1 + "#" + mapping.id, newState);
            }
        }

        Map<String, @Nullable String> esoKeyValueMap = ferroampMqttCommunication.getEsoChannelUpdateValues();
        for (ChannelMapping mapping : ChannelMapping.getESOMapping()) {
            State newState = StringType.valueOf(esoKeyValueMap.get(mapping.jsonKey));
            updateState("eso#" + mapping.id, newState);
        }

        Map<String, @Nullable String> esmKeyValueMap = ferroampMqttCommunication.getEsmChannelUpdateValues();
        for (ChannelMapping mapping : ChannelMapping.getESMMapping()) {
            State newState = StringType.valueOf(esmKeyValueMap.get(mapping.jsonKey));
            updateState("esm#" + mapping.id, newState);
        }
    }
}
