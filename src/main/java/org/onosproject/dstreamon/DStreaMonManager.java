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

import org.apache.commons.lang.NotImplementedException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.ovsdb.rfc.notation.Uuid;

import java.util.Set;

/**
 * Implements org.onosproject.dstreamon.DStreaMonService.
 */
@Component(immediate = true)
@Service
public class DStreaMonManager implements DStreaMonService {
    /**
     * Registers the data of a new stack created through OpenStack Heat.
     * Realized the port mirroring and creates the mgmt interface.
     *
     * @param stack the stack data to register
     */
    @Override
    public void registerStack(DStreaMonStack stack) {

    }

    /**
     * Uregisters a stack created previously through OpenStack Heat.
     * Delete also the port mirroring and the mgmt interface.
     *
     * @param stackuuid the stack uuid to unregister
     */
    @Override
    public void unregisterStack(Uuid stackuuid) {
        throw new NotImplementedException("unregisterStack not implemented");
    }

    /**
     * Returns the uuids of the registered stacks.
     *
     * @return the uuids set of the registered stacks.
     */
    @Override
    public Set<Uuid> getStacks() {
        throw new NotImplementedException("getStacks not implemented");
    }

    /**
     * Retrieves the data of a single stack.
     *
     * @param stackuuid the uuid of the stack to retrieve
     * @return the stack data
     */
    @Override
    public DStreaMonStack getStack(Uuid stackuuid) {
        throw new NotImplementedException("getStack not implemented");
    }

    /**
     * Returns the uuids of the management ports.
     *
     * @return the uuids set of the management ports.
     */
    @Override
    public Set<Uuid> getMgmtPorts() {
        throw new NotImplementedException("getMgmtPorts not implemented");
    }

    /**
     * Retrieves the name of the mgmt port.
     *
     * @param stackuuid the uuid of the associated stack
     * @return the name of the mgmt port
     */
    @Override
    public String getMgmtPort(Uuid stackuuid) {
        return null;
    }
}
