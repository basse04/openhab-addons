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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.binding.ferroamp.internal.DataUtil;
import org.openhab.binding.ferroamp.internal.FerroampBindingConstants;
import org.openhab.binding.ferroamp.internal.api.FerroampMqttCommunication;

import com.google.gson.Gson;

/**
 * @author Leo Siepel - Initial contribution
 */
@NonNullByDefault
class FerroampMqttCommunicationTest {

    private FerroampMqttCommunication communication = new FerroampMqttCommunication(null, null, null, 1);
    private Gson gson = new Gson();

    @Test
    void testProcessIncomingJsonMessageSso_ValidMessageForSso1() throws IOException {
        String messageJsonSso = DataUtil.fromFile("sso.json");

        communication.processMessage(FerroampBindingConstants.SSO_TOPIC, messageJsonSso.getBytes());

        assertNotNull(communication.ssoChannelsUpdateValues);
        assertEquals(9, communication.ssoChannelsUpdateValues.length);
        assertNotNull(communication.ssoChannelsUpdateValues[0]);
        assertEquals("29.766", communication.ssoChannelsUpdateValues[0].get("temp"));
    }

    @Test
    void testProcessIncomingJsonMessageSso_InvalidMessage() {
        String messageJsonSso = "{}";

        assertThrows(IllegalStateException.class,
                () -> communication.processMessage(FerroampBindingConstants.SSO_TOPIC, messageJsonSso.getBytes()));
    }

    @Test
    void testProcessIncomingJsonMessageSso_ValidMessageForSso2() throws IOException {
        String messageJsonSso = DataUtil.fromFile("multi_sso.json");

        communication.processMessage(FerroampBindingConstants.SSO_TOPIC, messageJsonSso.getBytes());

        assertNotNull(communication.ssoChannelsUpdateValues);
        assertEquals(9, communication.ssoChannelsUpdateValues.length);
        assertNotNull(communication.ssoChannelsUpdateValues[1]);
        assertEquals("19.166", communication.ssoChannelsUpdateValues[1].get("temp"));
    }
}
