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
package org.openhab.io.homekit.internal.accessories;

import java.util.List;

import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.HomekitException;
import org.openhab.io.homekit.internal.HomekitSettings;
import org.openhab.io.homekit.internal.HomekitTaggedItem;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.common.ProgrammableSwitchEventCharacteristic;
import io.github.hapjava.services.impl.DoorbellService;

/**
 * Implements a HomeKit Doorbell
 * 
 * This is only the primary service. To implement the entire video doorbell
 * profile, you also need to add a Camera RTP Stream Management service,
 * a Speaker service, and Microphone service.
 *
 * @author Cody Cutrer - Initial contribution
 */
class HomekitDoorbellImpl extends AbstractHomekitAccessoryImpl {

    public HomekitDoorbellImpl(HomekitTaggedItem taggedItem, List<HomekitTaggedItem> mandatoryCharacteristics,
            List<Characteristic> mandatoryRawCharacteristics, HomekitAccessoryUpdater updater,
            HomekitSettings settings) {
        super(taggedItem, mandatoryCharacteristics, mandatoryRawCharacteristics, updater, settings);
    }

    @Override
    public void init() throws HomekitException {
        super.init();
        addService(new DoorbellService(getCharacteristic(ProgrammableSwitchEventCharacteristic.class).get()));
    }
}
