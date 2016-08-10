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
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onlab.packet.VlanId;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.DefaultAnnotations;
import org.onosproject.net.DeviceId;
import org.onosproject.net.PortNumber;
import org.onosproject.net.behaviour.BridgeConfig;
import org.onosproject.net.behaviour.BridgeName;
import org.onosproject.net.behaviour.DefaultMirroringDescription;
import org.onosproject.net.behaviour.MirroringConfig;
import org.onosproject.net.behaviour.MirroringDescription;
import org.onosproject.net.behaviour.MirroringName;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;
import org.onosproject.ovsdb.rfc.notation.Uuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * Implements DStreaMonService.
 */
@Component(immediate = true)
@Service
public class DStreaMonManager implements DStreaMonService {

    private static final String DSTREAMON_APP = "org.onosproject.D-StreaMon";
    private static Logger log = LoggerFactory.getLogger(DStreaMonManager.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected DStreaMonStore dStreaMonStore;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected DriverService driverService;

    protected ApplicationId appId;

    protected MirroringConfig mirroringConfig;
    protected BridgeConfig bridgeConfig;

    private static final String ERROR_MIRRORING = "Impossible to Add Mirroring for stack %s";

    private static final String CANARY_OVSDB_ID = "ovsdb:172.16.131.1";
    private static final String BR_INT = "br-int";
    private static final String BR_MGMT = "br-mgmt";

    private Random random;




    @Activate
    protected void activate() {

        appId = coreService.registerApplication(DSTREAMON_APP);
        random = new Random();

        DeviceId deviceId = DeviceId.deviceId(CANARY_OVSDB_ID);
        DriverHandler h = driverService.createHandler(deviceId);
        mirroringConfig = h.behaviour(MirroringConfig.class);
        bridgeConfig = h.behaviour(BridgeConfig.class);

        log.info("Started");

    }

    @Deactivate
    protected void deactivate() {

        log.info("Stopped");

    }

    /**
     * Registers the data of a new stack created through OpenStack Heat.
     * Realized the port mirroring and creates the mgmt interface.
     *
     * @param stack the stack data to register
     */
    @Override
    public void registerStack(DStreaMonStack stack) throws DStreaMonException {

        dStreaMonStore.putStack(stack);

        /**
         * OpenStack Heat provides only the interfaces ids, for mirroring
         * we need the port uuid, thus we need this intermediate step of retrieving
         * port names.
         */
        List<String> ifaceids = Arrays.asList(stack.userPortUuid().value());
        List<PortNumber> ports = bridgeConfig.getLocalPorts(ifaceids);
        String userPortName = ports.get(0).name();
        ifaceids = Arrays.asList(stack.probePortUuid().value());
        ports = bridgeConfig.getLocalPorts(ifaceids);
        String probePortName = ports.get(0).name();

        MirroringName mirroringName = MirroringName.mirroringName(stack.stackUuid().value());
        List<String> selectSrcPorts = Arrays.asList(userPortName);
        List<String> selectDstPorts = Arrays.asList();
        List<VlanId> selectVlanIds = Arrays.asList();
        Optional<String> outputPort = Optional.of(probePortName);
        Optional<VlanId> outputVlan = Optional.empty();

        DefaultAnnotations.Builder optionBuilder = DefaultAnnotations.builder();
        MirroringDescription mirroringDescription = new DefaultMirroringDescription(
                mirroringName,
                selectSrcPorts,
                selectDstPorts,
                selectVlanIds,
                outputPort,
                outputVlan,
                optionBuilder.build()
        );

        if (mirroringConfig.addMirroring(BridgeName.bridgeName(BR_INT), mirroringDescription)) {

            String ifaceName = nextIfaceName();
            bridgeConfig.addPort(BridgeName.bridgeName(BR_MGMT), ifaceName);

            dStreaMonStore.putMgmtIface(stack.probeUuid(), ifaceName);

            return;

        }

        throw new DStreaMonException(String.format(ERROR_MIRRORING, stack.stackUuid()));

    }

    private String nextIfaceName() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
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
     * Returns the uuids of the probes.
     *
     * @return the uuids set of the probes.
     */
    @Override
    public Set<Uuid> getMgmtPorts() {
        throw new NotImplementedException("getMgmtPorts not implemented");
    }

    /**
     * Retrieves the name of the mgmt port.
     *
     * @param probeUuid the uuid of the associated probe
     * @return the name of the mgmt port
     */
    @Override
    public String getMgmtPort(Uuid probeUuid) throws DStreaMonException {
        return dStreaMonStore.getMgmtIface(probeUuid);
    }
}
