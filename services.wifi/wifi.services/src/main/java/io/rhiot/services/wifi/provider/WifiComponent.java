package io.rhiot.services.wifi.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.ProducerTemplate;
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
import org.apache.felix.scr.annotations.Service;
import org.eclipse.kura.net.wifi.WifiAccessPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.rhiot.services.wifi.api.WifiService;
import io.rhiot.services.wifi.entities.WifiEndpoint;
import io.rhiot.services.wifi.provider.builders.WifiRouteBuilder;

@Component(name = WifiComponent.COMPONENT_LABEL, description = WifiComponent.COMPONENT_DESCRIPTION, enabled = true, immediate = true)
@Properties({
		@Property(name = "camelContextId", description = "Wifi Camel Context", value = "rhiot.wifi.componentContext"),
		@Property(name = "active", description = "Property Active", value = "true") })
@References({
		@Reference(name = "camelComponent", referenceInterface = ComponentResolver.class, cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent") })
@Service(value = io.rhiot.services.wifi.api.WifiService.class)
public class WifiComponent extends AbstractCamelRunner implements WifiService {

	public static final String COMPONENT_LABEL = "io.rhiot.services.wifi";
	public static final String COMPONENT_DESCRIPTION = "Wifi SCR Component";

	private static final Logger log = LoggerFactory.getLogger(WifiComponent.class);

	public WifiComponent() {
		log.info("WifiComponent initializing");
	}

	@Override
	protected List<RoutesBuilder> getRouteBuilders() {
		log.info("WifiComponent::getRouteBuilders");
		List<RoutesBuilder> routesBuilders = new ArrayList<>();
		routesBuilders.add(new WifiRouteBuilder());
		return routesBuilders;
	}

	@Override
	public boolean isAvailable() {
		log.info("WifiComponent::isAvailable");
		return false;
	}

	@Override
	public List<WifiEndpoint> getWifiSummary() {
		log.info("WifiComponent::getWifiSummary");

		ProducerTemplate producer = context.createProducerTemplate();

		WifiAccessPoint[] wifiAccessPoints = producer.requestBody("direct:wifi", null, WifiAccessPoint[].class);

		log.info("Number of AccessPoints: " + wifiAccessPoints.length);

		List<WifiEndpoint> endpoints = new ArrayList<WifiEndpoint>();

		for (WifiAccessPoint wifiAccessPoint : wifiAccessPoints) {

			WifiEndpoint wifiEndpoint = new WifiEndpoint();
			wifiEndpoint.setSsid(wifiAccessPoint.getSSID());
			wifiEndpoint.setStrength(wifiAccessPoint.getStrength());
			endpoints.add(wifiEndpoint);

		}

		return endpoints;

	}

}