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

import org.onosproject.ovsdb.rfc.notation.Uuid;

import java.util.Set;

/**
 * Service that supports D-StreaMon use case providing the creation
 * of port mirrorings and management interfaces for the D-StreaMon
 * probes.
 */
public interface DStreaMonService {

    /**
     * Registers the data of a new stack created through OpenStack Heat.
     * Realized the port mirroring and creates the mgmt interface.
     *
     * @param stack the stack data to register
     */
    void registerStack(DStreaMonStack stack);

    /**
     * Uregisters a stack created previously through OpenStack Heat.
     * Delete also the port mirroring and the mgmt interface.
     *
     * @param stackuuid the stack uuid to unregister
     */
    void unregisterStack(Uuid stackuuid);

    /**
     * Returns the uuids of the registered stacks.
     *
     * @return the uuids set of the registered stacks.
     */
    Set<Uuid> getStacks();

    /**
     * Retrieves the data of a single stack.
     *
     * @param stackuuid the uuid of the stack to retrieve
     * @return the stack data
     */
    DStreaMonStack getStack(Uuid stackuuid);

    /**
     * Returns the uuids of the management ports.
     *
     * @return the uuids set of the management ports.
     */
    Set<Uuid> getMgmtPorts();

    /**
     * Retrieves the name of the mgmt port.
     *
     * @param stackuuid the uuid of the associated stack
     * @return the name of the mgmt port
     */
    String getMgmtPort(Uuid stackuuid);


}
