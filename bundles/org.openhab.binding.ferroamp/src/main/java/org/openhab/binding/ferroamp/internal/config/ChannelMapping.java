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
package org.openhab.binding.ferroamp.internal.config;

import java.util.ArrayList;
import java.util.List;

import javax.measure.Unit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.ferroamp.internal.FerroampBindingConstants;
import org.openhab.core.library.unit.Units;

/**
 * The {@link ChannelMapping} class defines methods, that set the channel configuration for the binding.
 *
 * @author Örjan Backsell - Initial contribution
 *
 */

@NonNullByDefault
public class ChannelMapping {

    public String id = "";
    public Unit<?> unit = Units.ONE;
    public String jsonKey = "";

    public ChannelMapping(String id, Unit<?> unit, String jsonKey) {
        this.id = id;
        this.unit = unit;
        this.jsonKey = jsonKey;
    }

    private static ChannelMapping cc(String id, Unit<?> unit) {
        return new ChannelMapping(id, unit, "");
    }

    private static ChannelMapping cc(String id, Unit<?> unit, String jsonKey) {
        return new ChannelMapping(id, unit, jsonKey);
    }

    public static List<ChannelMapping> getChannelConfigurationEhub() {
        final List<ChannelMapping> list = new ArrayList<>();

        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDFREQUENCY, Units.HERTZ));
        list.add(cc(FerroampBindingConstants.CHANNEL_ACECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ACECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ACECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDVOLTAGEL1, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDVOLTAGEL2, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDVOLTAGEL3, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERRMSCURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERRMSCURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERRMSCURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDCURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDCURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDCURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERLOADACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_APPARENTPOWER, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERACTIVEL1, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERACTIVEL2, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERACTIVEL3, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERREACTIVEL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERREACTIVEL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDPOWERREACTIVEL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERACTIVEL1, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERACTIVEL2, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERACTIVEL3, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERREACTIVEL1, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERREACTIVEL2, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERPOWERREACTIVEL3, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERREACTIVEL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERREACTIVEL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_CONSUMPTIONPOWERREACTIVEL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_SOLARPV, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_POSITIVEDCLINKVOLTAGE, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_NEGATIVEDCLINKVOLTAGE, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_GRIDENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_INVERTERENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_LOADENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_TOTALSOLARENERGY, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_STATE, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_TIMESTAMP, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_BATTERYENERGYPRODUCED, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_BATTERYENERGYCONSUMED, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_SOC, Units.PERCENT));
        list.add(cc(FerroampBindingConstants.CHANNEL_SOH, Units.PERCENT));
        list.add(cc(FerroampBindingConstants.CHANNEL_POWERBATTERY, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_TOTALCAPACITYBATTERIES, Units.WATT_HOUR));
        return list;
    }

    public static List<ChannelMapping> getSSOChannelMapping() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_ID, Units.ONE, "id"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_PV_VOLTAGE, Units.VOLT, "upv"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_PV_CURRENT, Units.AMPERE, "ipv"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_TOTAL_SOLAR_ENERGY, Units.WATT, "wpv"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_RELAY_STATUS, Units.ONE, "relaystatus"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_TEMPERATURE, Units.ONE, "temp"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_FAULT_CODE, Units.ONE, "faultcode"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_DC_LINK_VOLTAGE, Units.VOLT, "udc"));
        list.add(cc(FerroampBindingConstants.CHANNEL_SSO_TIMESTAMP, Units.ONE, "ts"));
        return list;
    }

    public static List<ChannelMapping> getChannelConfigurationEso() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOID, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOVOLTAGEBATTERY, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOCURRENTBATTERY, Units.AMPERE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOBATTERYENERGYPRODUCED, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOBATTERYENERGYCONSUMED, Units.WATT));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOSOC, Units.PERCENT));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESORELAYSTATUS, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOTEMPERATURE, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOFAULTCODE, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESODCLINKVOLTAGE, Units.VOLT));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESOTIMESTAMP, Units.ONE));
        return list;
    }

    public static List<ChannelMapping> getEsmMapping() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_ID, Units.ONE, "id"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_SOH, Units.PERCENT, "soh"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_SOC, Units.PERCENT, "soc"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_TOTAL_CAPACITY, Units.WATT_HOUR, "ratedcapacity"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_POWER_BATTERY, Units.WATT, "ratedpower"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_STATUS, Units.ONE, "status"));
        list.add(cc(FerroampBindingConstants.CHANNEL_ESM_TIMESTAMP, Units.ONE, "ts"));
        return list;
    }

    public static List<ChannelMapping> getChannelConfigurationRequest() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(FerroampBindingConstants.CHANNEL_REQUESTCHARGE, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_REQUESTDISCHARGE, Units.ONE));
        list.add(cc(FerroampBindingConstants.CHANNEL_AUTO, Units.ONE));
        return list;
    }
}
