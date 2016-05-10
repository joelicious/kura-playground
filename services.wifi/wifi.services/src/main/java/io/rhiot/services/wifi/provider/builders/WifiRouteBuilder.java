package io.rhiot.services.wifi.provider.builders;

import org.apache.camel.component.kura.KuraRouter;

public class WifiRouteBuilder extends KuraRouter {

	public WifiRouteBuilder() {

	}

	@Override
	public void configure() throws Exception {

		from("direct:wifi").routeId("rhiot.wifi.summary").log("Wifi Summary Invoked").to("kura-wifi:*/*");

	}
}