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

import static org.openhab.binding.ferroamp.internal.FerroampBindingConstants.*;

import java.util.ArrayList;
import java.util.List;

import javax.measure.Unit;

import org.eclipse.jdt.annotation.NonNullByDefault;
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

        list.add(cc(CHANNEL_GRIDFREQUENCY, Units.HERTZ));
        list.add(cc(CHANNEL_ACECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_ACECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_ACECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDVOLTAGEL1, Units.VOLT));
        list.add(cc(CHANNEL_GRIDVOLTAGEL2, Units.VOLT));
        list.add(cc(CHANNEL_GRIDVOLTAGEL3, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERRMSCURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERRMSCURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERRMSCURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDCURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDCURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDCURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADREACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADREACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADREACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADACTIVECURRENTL1, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADACTIVECURRENTL2, Units.AMPERE));
        list.add(cc(CHANNEL_INVERTERLOADACTIVECURRENTL3, Units.AMPERE));
        list.add(cc(CHANNEL_APPARENTPOWER, Units.WATT));
        list.add(cc(CHANNEL_GRIDPOWERACTIVEL1, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDPOWERACTIVEL2, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDPOWERACTIVEL3, Units.AMPERE));
        list.add(cc(CHANNEL_GRIDPOWERREACTIVEL1, Units.WATT));
        list.add(cc(CHANNEL_GRIDPOWERREACTIVEL2, Units.WATT));
        list.add(cc(CHANNEL_GRIDPOWERREACTIVEL3, Units.WATT));
        list.add(cc(CHANNEL_INVERTERPOWERACTIVEL1, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERPOWERACTIVEL2, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERPOWERACTIVEL3, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERPOWERREACTIVEL1, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERPOWERREACTIVEL2, Units.VOLT));
        list.add(cc(CHANNEL_INVERTERPOWERREACTIVEL3, Units.VOLT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERL1, Units.WATT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERL2, Units.WATT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERL3, Units.WATT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERREACTIVEL1, Units.WATT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERREACTIVEL2, Units.WATT));
        list.add(cc(CHANNEL_CONSUMPTIONPOWERREACTIVEL3, Units.WATT));
        list.add(cc(CHANNEL_SOLARPV, Units.WATT));
        list.add(cc(CHANNEL_POSITIVEDCLINKVOLTAGE, Units.VOLT));
        list.add(cc(CHANNEL_NEGATIVEDCLINKVOLTAGE, Units.VOLT));
        list.add(cc(CHANNEL_GRIDENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYPRODUCEDL1, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYPRODUCEDL2, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYPRODUCEDL3, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYCONSUMEDL1, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYCONSUMEDL2, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYCONSUMEDL3, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_GRIDENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_INVERTERENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYPRODUCEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_LOADENERGYCONSUMEDTOTAL, Units.WATT));
        list.add(cc(CHANNEL_TOTALSOLARENERGY, Units.WATT));
        list.add(cc(CHANNEL_STATE, Units.ONE));
        list.add(cc(CHANNEL_TIMESTAMP, Units.ONE));
        list.add(cc(CHANNEL_BATTERYENERGYPRODUCED, Units.WATT));
        list.add(cc(CHANNEL_BATTERYENERGYCONSUMED, Units.WATT));
        list.add(cc(CHANNEL_SOC, Units.PERCENT));
        list.add(cc(CHANNEL_SOH, Units.PERCENT));
        list.add(cc(CHANNEL_POWERBATTERY, Units.WATT));
        list.add(cc(CHANNEL_TOTALCAPACITYBATTERIES, Units.WATT_HOUR));
        return list;
    }

    public static List<ChannelMapping> getSSOMapping() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(CHANNEL_SSO_ID, Units.ONE, "id"));
        list.add(cc(CHANNEL_SSO_PV_VOLTAGE, Units.VOLT, "upv"));
        list.add(cc(CHANNEL_SSO_PV_CURRENT, Units.AMPERE, "ipv"));
        list.add(cc(CHANNEL_SSO_TOTAL_SOLAR_ENERGY, Units.WATT, "wpv"));
        list.add(cc(CHANNEL_SSO_RELAY_STATUS, Units.ONE, "relaystatus"));
        list.add(cc(CHANNEL_SSO_TEMPERATURE, Units.ONE, "temp"));
        list.add(cc(CHANNEL_SSO_FAULT_CODE, Units.ONE, "faultcode"));
        list.add(cc(CHANNEL_SSO_DC_LINK_VOLTAGE, Units.VOLT, "udc"));
        list.add(cc(CHANNEL_SSO_TIMESTAMP, Units.ONE, "ts"));
        return list;
    }

    public static List<ChannelMapping> getESOMapping() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(CHANNEL_ESO_ID, Units.ONE, "id"));
        list.add(cc(CHANNEL_ESO_VOLTAGE_BATTERY, Units.VOLT, "ubat"));
        list.add(cc(CHANNEL_ESO_CURRENT_BATTERY, Units.AMPERE, "ibat"));
        list.add(cc(CHANNEL_ESO_BATTERY_ENERGY_PRODUCED, Units.WATT, "wbatprod"));
        list.add(cc(CHANNEL_ESO_BATTERY_ENERGY_CONSUMED, Units.WATT, "wbatcons"));
        list.add(cc(CHANNEL_ESO_SOC, Units.PERCENT, "soc"));
        list.add(cc(CHANNEL_ESO_RELAY_STATUS, Units.ONE, "relaystatus"));
        list.add(cc(CHANNEL_ESO_TEMPERATURE, Units.ONE, "temp"));
        list.add(cc(CHANNEL_ESO_FAULT_CODE, Units.ONE, "faultcode"));
        list.add(cc(CHANNEL_ESO_DC_LINK_VOLTAGE, Units.VOLT, "udc"));
        list.add(cc(CHANNEL_ESO_TIMESTAMP, Units.ONE, "ts"));
        return list;
    }

    public static List<ChannelMapping> getESMMapping() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(CHANNEL_ESM_ID, Units.ONE, "id"));
        list.add(cc(CHANNEL_ESM_SOH, Units.PERCENT, "soh"));
        list.add(cc(CHANNEL_ESM_SOC, Units.PERCENT, "soc"));

        // TODO adapt CHANNEL_ESM_TOTAL_CAPACITY unit from joule to kwh
        list.add(cc(CHANNEL_ESM_TOTAL_CAPACITY, Units.WATT_HOUR, "ratedcapacity"));
        // TODO adapt CHANNEL_ESM_POWER_BATTERY unit to kw
        list.add(cc(CHANNEL_ESM_POWER_BATTERY, Units.WATT, "ratedpower"));
        list.add(cc(CHANNEL_ESM_STATUS, Units.ONE, "status"));
        list.add(cc(CHANNEL_ESM_TIMESTAMP, Units.ONE, "ts"));
        return list;
    }

    public static List<ChannelMapping> getChannelConfigurationRequest() {
        final List<ChannelMapping> list = new ArrayList<>();
        list.add(cc(CHANNEL_REQUEST_CHARGE, Units.ONE));
        list.add(cc(CHANNEL_REQUEST_DISCHARGE, Units.ONE));
        list.add(cc(CHANNEL_REQUEST_AUTO, Units.ONE));
        return list;
    }
}
