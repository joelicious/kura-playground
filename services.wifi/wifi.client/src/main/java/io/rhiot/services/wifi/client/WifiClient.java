package io.rhiot.services.wifi.client;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.rhiot.services.wifi.api.WifiService;
import io.rhiot.services.wifi.entities.WifiEndpoint;

@Component(name = WifiClient.COMPONENT_LABEL, immediate = true)
public class WifiClient {

	public static final String COMPONENT_LABEL = "io.rhiot.services.wifi.client";

	private static final Logger log = LoggerFactory.getLogger(WifiClient.class);

	private WifiService wifiService;

	public WifiClient() {
		log.info("WifiClient::initializing");
	}

	@Activate
	public void activate(Map<String, Object> configProps) {
		log.info("WifiClient::activate");

		List<WifiEndpoint> widiEndpoints = wifiService.getWifiSummary();

		for (WifiEndpoint wifiEndpoint : widiEndpoints) {

			log.info("SSID: " + wifiEndpoint.getSsid());
			log.info("Strength: " + wifiEndpoint.getStrength());

		}

	}

	@Deactivate
	public void deactivate(Map<?, ?> properties) {
		log.info("WifiClient::deactivate");
	}

	@Reference(name = "io.rhiot.services.wifi", service = WifiService.class, cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "lostWifiService")
	public void gotWifiService(final WifiService wifiService) {
		log.info("WifiClient::Setting Wifi Service");
		this.wifiService = wifiService;
	}

	public void lostWifiService(final WifiService wifiService) {
		log.info("WifiClient::Removing Wifi Service");
		this.wifiService = null;
	}

}