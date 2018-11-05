package com.adv.enhance.engine;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;

@Component
public class AdvReqEnhancementHandler {
	public static final Logger LOGGER = LoggerFactory.getLogger(AdvReqEnhancementHandler.class);

	private static final String PUBLISHER_BASE_URL = "http://159.89.185.155:3000";
	private static final String DEMOGRAPHY_BASE_URL = "http://159.89.185.155:3000";
	private static final String PUBLISHER_URL = "/api/publishers/find";
	private final String DEMOGRAPHY_URL = "/api/sites/{siteID}/demographics";
	/*
	 * since the request is not complex, build string to avoid object-string
	 * conversion. For complex JSON body creation use fasterxml.
	 */
	private final String PUBLISHER_REQUEST_BODY = "{\"q\":{\"siteID\":\"string\"}}";

	private final int MAXMIND_ACC_ID = 137236;
	private final String MAXMIND_LICENSE = "ZbKH74h9rgmO";

	public String getPublisher(String siteId) {
		return MiscUtil.sendPost(PUBLISHER_BASE_URL + PUBLISHER_URL, PUBLISHER_REQUEST_BODY.replace("string", siteId));
	}

	public String getDemography(String siteId) {
		String url = DEMOGRAPHY_BASE_URL + DEMOGRAPHY_URL.replace("{siteID}", siteId);
		return MiscUtil.sendGet(url);
	}

	public String getGeo(String ipAddr) throws IOException, GeoIp2Exception {
		try (WebServiceClient client = new WebServiceClient.Builder(MAXMIND_ACC_ID, MAXMIND_LICENSE).build()) {

			InetAddress ipAddress = InetAddress.getByName(ipAddr);
			CountryResponse response = client.country(ipAddress);
			return response.getCountry().getIsoCode();
		}
	}
}
