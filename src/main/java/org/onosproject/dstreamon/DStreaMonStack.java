/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.dstreamon;

import com.google.common.base.MoreObjects;
import org.onlab.packet.Ip4Address;
import org.onlab.packet.MacAddress;
import org.onosproject.ovsdb.rfc.notation.Uuid;

import java.util.Objects;

/**
 * Helper to store some useful information of a
 * stack created through OpenStack Heat.
 */
public final class DStreaMonStack {

    private Uuid stackUuid;
    private Uuid userUuid;
    private Uuid probeUuid;

    private Ip4Address userIp;
    private Ip4Address probeIp;

    private MacAddress userMac;
    private MacAddress probeMac;

    private Uuid userPortUuid;
    private Uuid probePortUuid;

    /**
     * Creates a new org.onosproject.dstreamon.DStreaMonStack.
     *
     * @param stackUuid the stack uuid
     * @param userUuid the user uuid
     * @param probeUuid the probe uuid
     * @param userIp the user vm ip
     * @param probeIp the probe vm ip
     * @param userMac the user vm mac
     * @param probeMac the probe vm mac
     * @param userPortUuid the user port uuid
     * @param probePortUuid the probe port uuid
     */
    public DStreaMonStack(Uuid stackUuid, Uuid userUuid, Uuid probeUuid,
                          Ip4Address userIp, Ip4Address probeIp,
                          MacAddress userMac, MacAddress probeMac,
                          Uuid userPortUuid, Uuid probePortUuid) {

        this.stackUuid = stackUuid;
        this.userUuid = userUuid;
        this.probeUuid = probeUuid;

        this.userIp = userIp;
        this.probeIp = probeIp;

        this.userMac = userMac;
        this.probeMac = probeMac;

        this.userPortUuid = userPortUuid;
        this.probePortUuid = probePortUuid;

    }

    /**
     * Creates a new org.onosproject.dstreamon.DStreaMonStack starting from the strings provided as input.
     *
     * @param stackUuid the stack uuid
     * @param userUuid the user uuid
     * @param probeUuid the probe uuid
     * @param userIp the user vm ip
     * @param probeIp the probe vm ip
     * @param userMac the user vm mac
     * @param probeMac the probe vm mac
     * @param userPortUuid the user port uuid
     * @param probePortUuid the probe port uuid
     * @return the created org.onosproject.dstreamon.DStreaMonStack
     */
    public static DStreaMonStack dStreaMonStack(String stackUuid, String userUuid, String probeUuid,
                                                String userIp, String probeIp,
                                                String userMac, String probeMac,
                                                String userPortUuid, String probePortUuid) {

        return new DStreaMonStack(Uuid.uuid(stackUuid), Uuid.uuid(userUuid), Uuid.uuid(probeUuid),
                                  Ip4Address.valueOf(userIp), Ip4Address.valueOf(probeIp),
                                  MacAddress.valueOf(userMac), MacAddress.valueOf(probeMac),
                                  Uuid.uuid(userPortUuid), Uuid.uuid(probePortUuid));

    }

    /**
     * Returns the Uuid of the stack.
     *
     * @return the stack uuid
     */
    public Uuid stackUuid() {
        return stackUuid;
    }

    /**
     * Returns the Uuid of the user.
     *
     * @return the user uuid
     */
    public Uuid userUuid() {
        return userUuid;
    }

    /**
     * Returns the Uuid of the probe.
     *
     * @return the probe uuid
     */
    public Uuid probeUuid() {
        return probeUuid;
    }

    /**
     * Returns the Ipv4 of the user.
     *
     * @return the user ip
     */
    public Ip4Address userIp() {
        return userIp;
    }

    /**
     * Returns the Ipv4 of the probe.
     *
     * @return the probe ip
     */
    public Ip4Address probeIp() {
        return probeIp;
    }

    /**
     * Returns the Mac address of the user.
     *
     * @return the user Mac address
     */
    public MacAddress userMac() {
        return userMac;
    }

    /**
     * Returns the Mac address of the probe.
     *
     * @return the probe Mac address
     */
    public MacAddress probeMac() {
        return probeMac;
    }

    /**
     * Return the Uuid of the user port.
     *
     * @return the uuid of the user port
     */
    public Uuid userPortUuid() {
        return userPortUuid;
    }

    /**
     * Return the Uuid of the probe port.
     *
     * @return the uuid of the probe port
     */
    public Uuid probePortUuid() {
        return probePortUuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stackUuid, userUuid, probeUuid,
                            userIp, probeIp,
                            userMac, probeMac,
                            userPortUuid, probePortUuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DStreaMonStack) {
            final DStreaMonStack other = (DStreaMonStack) obj;
            return  Objects.equals(this.stackUuid, other.stackUuid) &&
                    Objects.equals(this.userUuid, other.userUuid) &&
                    Objects.equals(this.probeUuid, other.probeUuid) &&
                    Objects.equals(this.userIp, other.userIp) &&
                    Objects.equals(this.probeIp, other.probeIp) &&
                    Objects.equals(this.userMac, other.userMac) &&
                    Objects.equals(this.probeMac, other.probeMac) &&
                    Objects.equals(this.userPortUuid, other.userPortUuid) &&
                    Objects.equals(this.probePortUuid, other.probePortUuid);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("stackUuid", stackUuid)
                .add("userUuid", userUuid)
                .add("probeUuid", probeUuid)
                .add("userIp", userIp)
                .add("probeIp", probeIp)
                .add("userMac", userMac)
                .add("probeMac", probeMac)
                .add("userPortUuid", userPortUuid)
                .add("probePortUuid", probePortUuid)
                .toString();
    }


}
