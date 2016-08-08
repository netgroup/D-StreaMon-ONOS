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

/**
 * Storage service for DStreaMon application.
 */
public interface DStreaMonStore {

    /**
     * Stores the stack given as input.
     *
     * @param stack the stack object
     * @throws DStreaMonException if stack uuid already exists
     */
    void putStack(DStreaMonStack stack) throws DStreaMonException;

    /**
     * Stores the mgmt interface's information.
     *
     * @param probeUuid the probe associated to the iface
     * @param ifaceName the iface name
     * @throws DStreaMonException if probe uuid already exists
     */
    void putMgmtIface(Uuid probeUuid, String ifaceName) throws DStreaMonException;

    /**
     * Retrieves the mgmt interface's information.
     *
     * @param probeUuid the probe associated to the iface
     * @return the ifacename
     * @throws DStreaMonException if probe uuid does not exist
     */
    String getMgmtIface(Uuid probeUuid) throws DStreaMonException;

}
