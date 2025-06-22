/*
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
package org.openhab.binding.ferroamp.internal.api;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.ferroamp.internal.FerroampBindingConstants;
import org.openhab.core.io.transport.mqtt.MqttBrokerConnection;
import org.openhab.core.io.transport.mqtt.MqttMessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * The {@link FerroampMqttCommunication} is responsible for communication with Ferroamp-system's Mqtt-broker.
 *
 * @author Örjan Backsell - Initial contribution
 *
 */

@NonNullByDefault
public class FerroampMqttCommunication implements MqttMessageSubscriber {

    public Map<String, @Nullable String>[] ssoChannelsUpdateValues = new ConcurrentHashMap[9];
    public Map<String, @Nullable String> esmChannelsUpdateValues = new ConcurrentHashMap<>();
    public Map<String, @Nullable String> esoChannelsUpdateValues = new ConcurrentHashMap<>();
    public Map<String, @Nullable String> ehubChannelsUpdateValues = new ConcurrentHashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(FerroampMqttCommunication.class);
    private @Nullable MqttBrokerConnection ferroampConnection;

    public FerroampMqttCommunication(String username, String password, String host, int port) {
        super();
        ferroampConnection = new MqttBrokerConnection(host, port, false, false, username);
        ferroampConnection.setCredentials(username, password);
    }

    public void start() {
        MqttBrokerConnection ferroampConnection = this.ferroampConnection;
        if (ferroampConnection == null) {
            logger.error("FerroampMqttCommunication: MqttBrokerConnection is null, cannot start connection");
            return;
        }

        ferroampConnection.start();
        ferroampConnection.subscribe(FerroampBindingConstants.EHUB_TOPIC, this);
        ferroampConnection.subscribe(FerroampBindingConstants.SSO_TOPIC, this);
        ferroampConnection.subscribe(FerroampBindingConstants.ESO_TOPIC, this);
        ferroampConnection.subscribe(FerroampBindingConstants.ESM_TOPIC, this);
    }

    // Handles request topic
    public void sendPublishedTopic(String payload) {
        MqttBrokerConnection ferroampConnection = this.ferroampConnection;
        if (ferroampConnection == null) {
            logger.error("FerroampMqttCommunication: MqttBrokerConnection is null, cannot publish message");
            return;
        }
        ferroampConnection.publish(FerroampBindingConstants.REQUEST_TOPIC, payload.getBytes(), 1, false);
    }

    // Capture actual Json-topic message
    @Override
    public void processMessage(String topic, byte[] payload) {
        if (FerroampBindingConstants.EHUB_TOPIC.equals(topic)) {
            processIncomingJsonMessageEhub(new String(payload, StandardCharsets.UTF_8));
        } else if (FerroampBindingConstants.SSO_TOPIC.equals(topic)) {
            processIncomingJsonMessageSso(new String(payload, StandardCharsets.UTF_8));
        } else if (FerroampBindingConstants.ESO_TOPIC.equals(topic)) {
            processIncomingJsonMessageEso(new String(payload, StandardCharsets.UTF_8));
        } else if (FerroampBindingConstants.ESM_TOPIC.equals(topic)) {
            processIncomingJsonMessageEsm(new String(payload, StandardCharsets.UTF_8));
        } else {
            logger.warn("Received message on unknown topic: {}", topic);
        }
    }

    public void processIncomingJsonMessageEhub(String messageJsonEhub) {
        esmChannelsUpdateValues = extractKeyValuePairs(messageJsonEhub, 0);
    }

    public Map<String, @Nullable String> extractKeyValuePairs(String json, int deviceIndex) {
        Map<String, @Nullable String> result = new ConcurrentHashMap<>();
        JsonArray arr;
        try {
            arr = JsonParser.parseString(json).getAsJsonArray();
        } catch (JsonSyntaxException e) {
            logger.warn("Failed to parse JSON: {}", e.getMessage());
            return Collections.emptyMap();
        }
        JsonObject obj = arr.get(deviceIndex).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            String key = entry.getKey();
            JsonObject valueObj = entry.getValue().getAsJsonObject();

            // Prefer "val" if present
            if (valueObj.has("val")) {
                result.put(key, valueObj.get("val").getAsString());
            }

            // Handle phase keys (L1, L2, L3)
            for (String phase : new String[] { "L1", "L2", "L3" }) {
                if (valueObj.has(phase)) {
                    result.put(key + "." + phase, valueObj.get(phase).getAsString());
                }
            }

            // Handle "pos" and "neg"
            if (valueObj.has("pos")) {
                result.put(key + ".pos", valueObj.get("pos").getAsString());
            }
            if (valueObj.has("neg")) {
                result.put(key + ".neg", valueObj.get("neg").getAsString());
            }
        }

        return result;
    }

    // Prepare actual Json-topic Sso-messages and update values for channels
    void processIncomingJsonMessageSso(String messageJsonSso) {
        JsonArray ssoArray = JsonParser.parseString(messageJsonSso).getAsJsonArray();
        for (int ssoIndex = 0; ssoIndex < ssoArray.size(); ssoIndex++) {
            ssoChannelsUpdateValues[ssoIndex] = extractKeyValuePairs(messageJsonSso, ssoIndex);
        }
    }

    // Prepare actual Json-topic Eso-message and update values for channels
    void processIncomingJsonMessageEso(String messageJsonEso) {
        esoChannelsUpdateValues = extractKeyValuePairs(messageJsonEso, 0);
    }

    // Prepare actual Json-topic Esm-message and update values for channels
    void processIncomingJsonMessageEsm(String messageJsonEsm) {
        esmChannelsUpdateValues = extractKeyValuePairs(messageJsonEsm, 0);
    }

    public void stop() {
        MqttBrokerConnection localSubscribeConnection = ferroampConnection;
        if (localSubscribeConnection != null) {
            localSubscribeConnection.unsubscribe(FerroampBindingConstants.EHUB_TOPIC, this);
            localSubscribeConnection.unsubscribe(FerroampBindingConstants.SSO_TOPIC, this);
            localSubscribeConnection.unsubscribe(FerroampBindingConstants.ESO_TOPIC, this);
            localSubscribeConnection.unsubscribe(FerroampBindingConstants.ESM_TOPIC, this);
            localSubscribeConnection.stop();
        }
    }

    public void dispose() {
        stop();
        this.ferroampConnection = null;
    }

    public boolean isConnected() {
        MqttBrokerConnection ferroampConnection = this.ferroampConnection;
        return !(ferroampConnection == null || "DISCONNECTED".equals(ferroampConnection.connectionState().toString()));
    }
}
