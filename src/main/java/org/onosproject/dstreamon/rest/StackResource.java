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
package org.onosproject.dstreamon.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.onosproject.dstreamon.DStreaMonException;
import org.onosproject.dstreamon.DStreaMonService;
import org.onosproject.dstreamon.DStreaMonStack;
import org.onosproject.ovsdb.rfc.notation.Uuid;
import org.onosproject.rest.AbstractWebResource;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Web resource for storing Stack objects.
 */
@Path("stacks")
public class StackResource extends AbstractWebResource {

    private static final Logger log = getLogger(StackResource.class);
    private static final String INVALID_PARAMETER = "INVALID_PARAMETER\n";
    private static final String REGISTERED = "REGISTERED\n";
    private static final String FAILED = "FAILED\n";
    private static final String OK = "OK\n";


    /**
     * Registers the data of a new stack created through OpenStack Heat.
     *
     * @param stream the input stream
     * @return the result of the operation
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerStack(InputStream stream) {

        ObjectNode root = mapper().createObjectNode();
        DStreaMonStack stack = null;
        String stackUuid = null;
        JsonNode vm = null;
        String userUuid = null;
        String userIp = null;
        String userMac = null;
        String userPortUuid = null;
        String probeUuid = null;
        String probeIp = null;
        String probeMac = null;
        String probePortUuid = null;

        try {

            root = (ObjectNode) mapper().readTree(stream);
            stackUuid = root.get("stack_uuid").asText();
            vm = root.get("user");
            userUuid = vm.get("uuid").asText();
            userIp = vm.get("ip").asText();
            userMac = vm.get("mac").asText();
            userPortUuid = vm.get("port_uuid").asText();
            vm = root.get("probe");
            probeUuid = vm.get("uuid").asText();
            probeIp = vm.get("ip").asText();
            probeMac = vm.get("mac").asText();
            probePortUuid = vm.get("port_uuid").asText();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.ok(INVALID_PARAMETER).build();
        }


        try {
            stack = DStreaMonStack
                    .dStreaMonStack(stackUuid, userUuid, probeUuid,
                                    userIp, probeIp,
                                    userMac, probeMac,
                                    userPortUuid, probePortUuid);
            DStreaMonService service = get(DStreaMonService.class);
            service.registerStack(stack);

        } catch (Exception e) {
            log.info(e.getMessage());
            return Response.ok(FAILED).build();
        }

        return Response.ok(REGISTERED).build();

    }

    /**
     * Uregisters a stack  previously created.
     *
     * @param stackUuid the stack uuid
     * @return the result of the operation
     */
    @DELETE
    @Path("{stackUuid}")
    public Response unregisterStack(@PathParam("stackUuid") String stackUuid) {

        log.info("Unmarshaled Stack Uuid {}", stackUuid);
        return Response.ok(OK).build();

    }

    /**
     * Returns the uuids of the registered stacks.
     *
     * @return the result of the operation
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStacks() {
        Iterable<Uuid> stacks = get(DStreaMonService.class).getStacks();
        /**
         * FIXME
         * Check if Uuid are serializable
         */
        return ok(encodeArray(Uuid.class, "stacks", stacks)).build();
    }

    /**
     * Retrieves the data of a single stack.
     *
     * @param stackUuid the uuid of the stack to retrieve
     * @return the result of the operation
     */
    @GET
    @Path("{stackUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStack(@PathParam("stackUuid") String stackUuid) {
        DStreaMonStack stack = get(DStreaMonService.class).getStack(Uuid.uuid(stackUuid));
        /**
         * FIXME
         * Check if DStreaMonStack is serializable
         */
        return ok(codec(DStreaMonStack.class).encode(stack, this)).build();
    }

}
