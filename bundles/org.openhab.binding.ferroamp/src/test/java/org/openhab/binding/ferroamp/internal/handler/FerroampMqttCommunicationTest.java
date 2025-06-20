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
package org.openhab.binding.ferroamp.internal.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openhab.binding.ferroamp.internal.DataUtil;
import org.openhab.binding.ferroamp.internal.api.FerroampMqttCommunication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author Leo Siepel - Initial contribution
 */
@NonNullByDefault
class FerroampMqttCommunicationTest {

    private FerroampMqttCommunication communication = new FerroampMqttCommunication();
    private Gson gson = new Gson();

    @Test
    @Disabled
    void testProcessIncomingJsonMessageSso_ValidMessageForSso1() throws IOException {
        String topic = "test/topic";
        String messageJsonSso = DataUtil.fromFile("sso.json");

        communication.ssoS1IdCheck = "sso1";

        communication.processIncomingJsonMessageSso(topic, messageJsonSso);

        assertNotNull(communication.ssoS1ChannelsUpdateValues);
        assertEquals(9, communication.ssoS1ChannelsUpdateValues.length);
        assertEquals("value1", communication.ssoS1ChannelsUpdateValues[0]);
    }

    @Test
    @Disabled
    void testProcessIncomingJsonMessageSso_InvalidMessage() {
        String topic = "test/topic";
        String messageJsonSso = "{}";

        communication.processIncomingJsonMessageSso(topic, messageJsonSso);

        assertNull(communication.ssoS1ChannelsUpdateValues);
        assertNull(communication.ssoS2ChannelsUpdateValues);
        assertNull(communication.ssoS3ChannelsUpdateValues);
        assertNull(communication.ssoS4ChannelsUpdateValues);
    }

    @Test
    @Disabled
    void testProcessIncomingJsonMessageSso_ValidMessageForSso2() {
        String topic = "test/topic";
        String messageJsonSso = createValidSsoMessage("sso2");

        communication.ssoS2IdCheck = "sso2";

        communication.processIncomingJsonMessageSso(topic, messageJsonSso);

        assertNotNull(communication.ssoS2ChannelsUpdateValues);
        assertEquals(9, communication.ssoS2ChannelsUpdateValues.length);
        assertEquals("value1", communication.ssoS2ChannelsUpdateValues[0]);
    }

    private String createValidSsoMessage(String ssoId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("0", ssoId);
        for (int i = 1; i <= 8; i++) {
            jsonObject.addProperty(String.valueOf(i), "value" + i);
        }
        return gson.toJson(jsonObject);
    }
}
