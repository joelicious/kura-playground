package io.rhiot.services.wifi.entities;

public class WifiEndpoint {

	private String ssid;

	private int strength;

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

}