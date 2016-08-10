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

import org.onosproject.dstreamon.DStreaMonException;
import org.onosproject.dstreamon.DStreaMonService;
import org.onosproject.ovsdb.rfc.notation.Uuid;
import org.onosproject.rest.AbstractWebResource;
import org.slf4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Web resource for retrieving MgmtInterface objects.
 */
@Path("mgmts")
public class MgmtInterfaceResource extends AbstractWebResource {

    private static final Logger log = getLogger(MgmtInterfaceResource.class);
    private static final String INVALID_PARAMETER = "INVALID_PARAMETER\n";
    private static final String FAILED = "FAILED\n";
    private static final String OK = "OK\n";

    /**
     * Returns the uuids of the probes.
     *
     * @return the result of the operation
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMgmtPorts() {
        Iterable<Uuid> probeUuids = get(DStreaMonService.class).getMgmtPorts();
        /**
         * FIXME
         * Check if Uuid are serializable
         */
        return ok(encodeArray(Uuid.class, "probeUuids", probeUuids)).build();
    }

    /**
     * Retrieves the name of the mgmt port.
     *
     * @param probeUuid the associated probe uuid
     * @return the result of the operation
     */
    @GET
    @Path("{probeUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMgmtPort(@PathParam("probeUuid") String probeUuid) {
        String ifaceName = null;
        try {
            ifaceName = get(DStreaMonService.class).getMgmtPort(Uuid.uuid(probeUuid));
        } catch (DStreaMonException e) {
            log.info(e.getMessage());
            return Response.ok(FAILED).build();
        }
        return ok(mapper().createObjectNode().put("iface", ifaceName)).build();
    }

}
