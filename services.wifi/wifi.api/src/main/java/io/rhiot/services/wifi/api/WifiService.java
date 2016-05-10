package io.rhiot.services.wifi.api;

import java.util.List;

import io.rhiot.services.wifi.entities.WifiEndpoint;

public interface WifiService {

	public boolean isAvailable();

	public List<WifiEndpoint> getWifiSummary();

}