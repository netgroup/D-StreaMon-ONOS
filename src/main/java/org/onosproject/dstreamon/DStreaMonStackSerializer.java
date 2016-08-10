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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.onlab.packet.Ip4Address;
import org.onlab.packet.MacAddress;
import org.onosproject.ovsdb.rfc.notation.Uuid;

/**
 * Kryo serializer for SDX-L2 connection point.
 */
public class DStreaMonStackSerializer extends Serializer<DStreaMonStack> {

    /**
     * Serialize the object using kryo.
     *
     * @param kryo the serializer
     * @param output the output
     * @param object the object to serialize
     */
    public void write(Kryo kryo, Output output, DStreaMonStack object) {
        kryo.writeClassAndObject(output, object.stackUuid().value());
        kryo.writeClassAndObject(output, object.userUuid().value());
        kryo.writeClassAndObject(output, object.probeUuid().value());
        kryo.writeClassAndObject(output, object.userIp());
        kryo.writeClassAndObject(output, object.probeIp());
        kryo.writeClassAndObject(output, object.userMac());
        kryo.writeClassAndObject(output, object.probeMac());
        kryo.writeClassAndObject(output, object.userPortUuid().value());
        kryo.writeClassAndObject(output, object.probePortUuid().value());
    }

    /**
     * Create an object from one serialized using kryo.
     *
     * @param kryo the serializer
     * @param input the inpunt
     * @param type the object to create
     * @return the object
     */
    public DStreaMonStack read(Kryo kryo, Input input, Class<DStreaMonStack> type) {
        Uuid stackUuid = Uuid.uuid((String) kryo.readClassAndObject(input));
        Uuid userUuid = Uuid.uuid((String) kryo.readClassAndObject(input));
        Uuid probeUuid = Uuid.uuid((String) kryo.readClassAndObject(input));

        Ip4Address userIp = (Ip4Address) kryo.readClassAndObject(input);
        Ip4Address probeIp = (Ip4Address) kryo.readClassAndObject(input);

        MacAddress userMac = (MacAddress) kryo.readClassAndObject(input);
        MacAddress probeMac = (MacAddress) kryo.readClassAndObject(input);

        Uuid userPortUuid = Uuid.uuid((String) kryo.readClassAndObject(input));
        Uuid probePortUuid = Uuid.uuid((String) kryo.readClassAndObject(input));

        return new DStreaMonStack(stackUuid, userUuid, probeUuid,
                                  userIp, probeIp,
                                  userMac, probeMac,
                                  userPortUuid, probePortUuid
        );
    }
}
