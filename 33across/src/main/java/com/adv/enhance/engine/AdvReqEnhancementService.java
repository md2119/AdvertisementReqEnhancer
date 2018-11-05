package com.adv.enhance.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adv.enhance.api.ISite;
import com.adv.enhance.base.Site;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.geoip2.exception.GeoIp2Exception;

@Service
public class AdvReqEnhancementService {
	public static final Logger LOGGER = LoggerFactory.getLogger(AdvReqEnhancementService.class);

	@Autowired
	AdvReqEnhancementHandler rHandler;

	// keep a map of demography properties that should be added to enhanced site
	private static final Map<String, String> DEMOGRAPHY_PROPERTY_MAP = new HashMap<String, String>();
	static {
		DEMOGRAPHY_PROPERTY_MAP.put("male_percent", "pct_female");
		DEMOGRAPHY_PROPERTY_MAP.put("female_percent", "pct_female");
	}

	// TODO: Separate out the processors
	public JsonNode getEnhancedSite(String request) throws JsonParseException, IOException {

		JsonNode origReqJson = MiscUtil.stringToJson(request);
		JsonNode site = origReqJson.path("site");
		String siteId = site.get("id").asText();

		String publisher = getPublisher(siteId);
		JsonNode publisherJson = MiscUtil.stringToJson(publisher).get("publisher");
		MiscUtil.addJson(site, "publisher", publisherJson);

		String demography = getDemography(siteId);
		String femalePercent = MiscUtil.stringToJson(demography).path("demographics").get("pct_female").asText();
		int female_percent = (int) Math.round(Double.parseDouble(femalePercent));
		int male_percent = 100 - female_percent;
		JsonNode demographyJson = MiscUtil.createEmptyJson("demography");
		MiscUtil.addJson(demographyJson, "male_percent", String.valueOf(male_percent));
		MiscUtil.addJson(demographyJson, "female_percent", String.valueOf(female_percent));
		MiscUtil.addJson(site, "demography", demographyJson);

		JsonNode deviceJson = origReqJson.path("device");
		String ipAddr = deviceJson.get("ip").asText();
		String countryCode = getDeviceContext(ipAddr);
		JsonNode geoJson = MiscUtil.createEmptyJson("geo");
		MiscUtil.addJson(geoJson, "country", countryCode);
		MiscUtil.addJson(deviceJson, "geo", geoJson);

		return origReqJson;
	}

	// TODO
	public Site getEnhancedSite(ISite site) {
		return null;
	}

	public String getDemography(String siteId) {

		return rHandler.getDemography(siteId);
	}

	public String getPublisher(String siteId) {
		return rHandler.getPublisher(siteId);
	}

	public String getDeviceContext(String ipAddr) {
		return getGeo(ipAddr);
	}

	public String getGeo(String ipAddr) {
		try {
			return rHandler.getGeo(ipAddr);
		} catch (IOException | GeoIp2Exception e) {
			LOGGER.error("Cannot get geo data", e);
			throw new RuntimeException("Cannot get geo data", e);
		}
	}
}
