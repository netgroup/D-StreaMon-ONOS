![CNIT logo](http://www.greenicn.org/wp-content/uploads/2013/11/Logo_CNIT.png "http://www.cnit.it")

D-StreaMon-ONOS
==========================

D-StreaMon companion application for Open Networking Operating System

License
=======

This sofware is licensed under the Apache License, Version 2.0.

Information can be found here:
 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).


D-StreaMon dependencies
=============================


D-StreaMon installation
=============================

- Compile the project:

        mci

    or to skip unit tests:

        mci -Dmaven.test.skip=true

- Install in your ONOS deployment:

        onos-app $ONOS_VM_IP install target/onos-app-sdx-l2-1.7.0-SNAPSHOT.oar


D-StreaMon CLI commands
=============================

TBD

D-StreaMon REST APIs
=============================

- To register a new Heat Stack:

        POST http://$ONOS_VM_IP:8181/onos/d-streamon/stacks

        {
        	"stack_uuid": "a68decaa-97fa-46a0-b1c6-ccf39fa61f4b",
        	"user": {
        		"uuid": "0a9e9ce4-301e-4bcb-a0c4-eb00930f27b2",
        		"ip": "10.0.10.60",
        		"mac": "fa:16:3e:58:73:5d",
        		"port_uuid": "a17772e6-e451-46e5-838f-73a2adc0d2a9"
        	},
        	"probe": {
        		"uuid": "e3f57693-dc80-42d9-b6df-c963eb92bbe0",
        		"ip": "10.0.10.59",
        		"mac": "fa:16:3e:cb:9b:51",
        		"port_uuid": "81765d17-d822-4ff3-baf8-019fe81b1f45"
        	}
        }

        REGISTERED, FAILED, INVALID PARAMETER

- To retrieve the management interface:

        GET http://$ONOS_VM_IP:8181/onos/d-streamon/mgmts/{probe.uuid}

        {
            "iface":"yic77g"
        }

        FAILED

D-StreaMon Tips
=============================

- ONOS uses basic authentication for REST APIs

        user=onos
        pswd=rocks
