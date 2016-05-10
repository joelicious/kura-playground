package io.rhiot.playground.rest.provider.builders;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import io.rhiot.playground.rest.provider.processors.WifiProcessor;
import io.rhiot.services.wifi.entities.WifiEndpoint;

public class PlaygroundRestRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// @formatter:off
		
		restConfiguration().component("coap").host("192.168.1.175").port(8082)
			.bindingMode(RestBindingMode.json).dataFormatProperty("prettyPrint", "true");

		rest("/rhiot").description("Rhiot Rest Service")
			.get("/hello").id("rhiot.playground.Hello").description("Says Hello")
				.outType(String.class).produces("application/json").to("direct:hello")
			.get("/wifi").id("rhiot.playground.Wifi").description("Wifi Summary")
				.outType(WifiEndpoint.class).produces("application/json").to("direct:wifi");

		from("direct:hello").routeId("rhiot.playground.helloRoute").log("/rhiot/hello invoked")
			.transform().constant("Hello World");
		
		from("direct:wifi").routeId("rhiot.playground.wifiRoute").log("/rhiot/wifi invoked")
			.process(new WifiProcessor());
		
		// @formatter:on

	}
}
