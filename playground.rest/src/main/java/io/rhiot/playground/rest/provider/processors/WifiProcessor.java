package io.rhiot.playground.rest.provider.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.rhiot.services.wifi.api.WifiService;
import io.rhiot.services.wifi.entities.WifiEndpoint;

public class WifiProcessor implements Processor {

	private static final Logger log = LoggerFactory.getLogger(WifiProcessor.class);

	private WifiService wifiService = null;

	@Override
	public void process(Exchange exchange) throws Exception {

		log.info("WifiProcessor::process");

		List<WifiEndpoint> wifiSummary = null;

		if (null == wifiService) {
			log.error("Unavailable");
		} else {
			wifiSummary = wifiService.getWifiSummary();
		}

		exchange.getIn().setBody(wifiSummary);

	}

	public void setWifiService(WifiService wifiService) {
		this.wifiService = wifiService;
	}

	public void unsetWifiService() {
		this.wifiService = null;
	}

}