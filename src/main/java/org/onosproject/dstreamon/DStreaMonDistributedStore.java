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

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onlab.util.KryoNamespace;
import org.onosproject.ovsdb.rfc.notation.Uuid;
import org.onosproject.store.serializers.KryoNamespaces;
import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.Serializer;
import org.onosproject.store.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DStreaMon Store implementation backed by different distributed primitives.
 */
@Component(immediate = true)
@Service
public class DStreaMonDistributedStore implements DStreaMonStore {

    private static Logger log = LoggerFactory.getLogger(DStreaMonDistributedStore.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    private StorageService storageService;

    private Map<String, DStreaMonStack> dStreaMonStackMap;
    private ConsistentMap<String, DStreaMonStack> dStreaMonStackConsistentMap;

    private Map<String, String> dstreamonMgmtIfaceMap;
    private ConsistentMap<String, String> dstreamonMgmtIfaceConsistentMap;

    private static String errorPutStack = "It is not possible to add %s stack " +
            "because it exists";

    private static String errorPutMgmtIface = "It is not possible to add %s iface " +
            "because it exists";

    private static String errorGetMgmtIface = "It is not possible to retrieve %s iface " +
            "because it does not exist";

    /**
     * Helper class called to initialise tests.
     */
    public void initForTest() {
        this.dStreaMonStackMap = new ConcurrentHashMap<String, DStreaMonStack>();
        this.dstreamonMgmtIfaceMap = new ConcurrentHashMap<String, String>();
    }

    /**
     * Activates the implementation of the DStreaMon store.
     */
    @Activate
    public void activate() {

        KryoNamespace custom = KryoNamespace.newBuilder()
                .register(KryoNamespaces.API)
                .nextId(KryoNamespaces.BEGIN_USER_CUSTOM_ID)
                .register(new DStreaMonStackSerializer(), new Class[]{DStreaMonStack.class})
                .build();

        dStreaMonStackConsistentMap = this.storageService
                .<String, DStreaMonStack>consistentMapBuilder()
                .withSerializer(Serializer.using(custom))
                .withName("dStreaMonStackConsistentMap")
                .build();
        dStreaMonStackMap = dStreaMonStackConsistentMap.asJavaMap();

        dstreamonMgmtIfaceConsistentMap = this.storageService
                .<String, String>consistentMapBuilder()
                .withSerializer(Serializer.using(custom))
                .withName("dstreamonMgmtIfaceConsistentMap")
                .build();
        dstreamonMgmtIfaceMap = dstreamonMgmtIfaceConsistentMap.asJavaMap();

        log.info("Started");
    }

    /**
     * Deactivates the implementation of the DStreaMon store.
     */
    @Deactivate
    public void deactivate() {
        log.info("Stopped");
    }

    /**
     * Stores the stack given as input.
     *
     * @param stack the stack object
     * @throws DStreaMonException if stack uuid already exists
     */
    @Override
    public void putStack(DStreaMonStack stack) throws DStreaMonException {

        DStreaMonStack previous = dStreaMonStackMap.putIfAbsent(stack.stackUuid().value(), stack);
        if (previous != null && previous.equals(stack)) {
            throw new DStreaMonException(String.format(errorPutStack, stack.stackUuid().value()));
        }

    }

    /**
     * Stores the mgmt interface's information.
     *
     * @param probeUuid the probe associated to the iface
     * @param ifaceName the iface name
     * @throws DStreaMonException if probe uuid already exists
     */
    @Override
    public void putMgmtIface(Uuid probeUuid, String ifaceName) throws DStreaMonException {

        String previous = dstreamonMgmtIfaceMap.putIfAbsent(probeUuid.value(), ifaceName);
        if (previous != null && previous.equals(ifaceName)) {
            throw new DStreaMonException(String.format(errorPutMgmtIface, ifaceName));
        }

    }

    /**
     * Retrieves the mgmt interface's information.
     *
     * @param probeUuid the probe associated to the iface
     * @return the ifacename
     * @throws DStreaMonException if probe uuid does not exist
     */
    @Override
    public String getMgmtIface(Uuid probeUuid) throws DStreaMonException {

        String ifaceName = dstreamonMgmtIfaceMap.getOrDefault(probeUuid.value(), null);
        if (ifaceName == null) {
            throw new DStreaMonException(String.format(errorGetMgmtIface, ifaceName));
        }
        return ifaceName;

    }
}
