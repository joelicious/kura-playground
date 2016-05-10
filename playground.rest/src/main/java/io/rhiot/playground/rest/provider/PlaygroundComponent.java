package io.rhiot.playground.rest.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.scr.AbstractCamelRunner;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.References;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.rhiot.playground.rest.provider.builders.PlaygroundRestRouteBuilder;
import io.rhiot.playground.rest.provider.processors.WifiProcessor;
import io.rhiot.services.wifi.api.WifiService;

@Component(name = PlaygroundComponent.COMPONENT_LABEL, description = PlaygroundComponent.COMPONENT_DESCRIPTION, immediate = true, metatype = true)
@Properties({ @Property(name = "camelContextId", value = "rhiot.wifi.componentContext"),
		@Property(name = "active", value = "true") })
@References({
		@Reference(name = "camelComponent", referenceInterface = ComponentResolver.class, cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent"),
		@Reference(name = "io.rhiot.services.wifi", referenceInterface = WifiService.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC, bind = "setWifiService", unbind = "unsetWifiService") })
public class PlaygroundComponent extends AbstractCamelRunner {

	public static final String COMPONENT_LABEL = "io.rhiot.playground.rest";
	public static final String COMPONENT_DESCRIPTION = "Playground Rest SCR Camel Context";

	private static final Logger log = LoggerFactory.getLogger(PlaygroundComponent.class);

	private WifiProcessor wifiProcessor;

	public PlaygroundComponent() {
		log.info("PlaygroundComponent::Constructor");
	}

	@Override
	protected List<RoutesBuilder> getRouteBuilders() {
		List<RoutesBuilder> routesBuilders = new ArrayList<>();
		routesBuilders.add(new PlaygroundRestRouteBuilder());
		return routesBuilders;
	}

	public void setWifiService(final WifiService wifiService) {
		log.info("PlaygroundComponent::setWifiService");
		wifiProcessor.setWifiService(wifiService);
	}

	public void unsetWifiService(final WifiService wifiService) {
		log.info("PlaygroundComponent::unsetWifiService");
		wifiProcessor.unsetWifiService();
	}

}