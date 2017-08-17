package com.stc.AuctionTools.services;

import static com.stc.echannels.esdp.client.impl.Constants.ALGORITHM;
import static com.stc.echannels.esdp.client.impl.Constants.ARABIC_LOCALE;
import static com.stc.echannels.esdp.client.impl.Constants.AUTH_HEADER_BASIC;
import static com.stc.echannels.esdp.client.impl.Constants.AUTH_STCWS_PREFIX;
import static com.stc.echannels.esdp.client.impl.Constants.COLON;
import static com.stc.echannels.esdp.client.impl.Constants.DATE_FORMAT;
import static com.stc.echannels.esdp.client.impl.Constants.DEFAULT_TIMEZONE;
import static com.stc.echannels.esdp.client.impl.Constants.ENCODING;
import static com.stc.echannels.esdp.client.impl.Constants.USER_AUTH_HEADER;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.stc.echannels.esdp.client.impl.EsdpSpringClientServiceImpl;

@Service
public class UtilityServices {
	
	@Value(value="${stc.rest.client.password}")
	String clientPassword;
	@Value(value="${stc.rest.client.username}")
	String clientUsername;
	
	private static final Logger logger = LoggerFactory
			.getLogger(EsdpSpringClientServiceImpl.class);

	private String sign(String date, String secretKey) throws Exception {
		SecretKey signingKey = new SecretKeySpec(secretKey.getBytes(ENCODING),
				ALGORITHM);
		Mac mac = Mac.getInstance(ALGORITHM);
		mac.init(signingKey);
		// Signed String must be BASE64 encoded.
		byte[] signedBytes = mac.doFinal(date.getBytes(ENCODING));
		return new String(Base64Utils.encode(signedBytes));
	}
	
	public MultiValueMap<String, String> buildHeaders(Locale locale, String username, String password,String useragent, String language) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
		logger.debug("buildHeaders, locale: {}, username: {}", locale, username);
		String signature = null;
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(DEFAULT_TIMEZONE);
		String date = DATE_FORMAT.format(cal.getTime());
		try {
			signature = sign(date, clientPassword);
			logger.trace("buildHeaders signature:{}", signature);
		} catch (Exception e) {
			logger.warn("Exception thrown while generating signature: " + e,
					e);
		}

//		headers.add("Accepted-Language",Arrays.asList(StandardCharsets.UTF_8).toString());
		headers.add(HttpHeaders.DATE, date);
		headers.add(HttpHeaders.AUTHORIZATION, AUTH_STCWS_PREFIX.concat(clientUsername).concat(COLON).concat(signature));

		if (StringUtils.hasLength(useragent))
			headers.add(HttpHeaders.USER_AGENT, useragent);
		// TODO should be passed from the API user, or check spring local
		// context holder, or provide a service setter method for it.
		if (locale != null && (ARABIC_LOCALE.getLanguage()
				.equals(locale.getLanguage())
				|| Locale.ENGLISH.getLanguage().equals(locale.getLanguage())))
			headers.add(HttpHeaders.ACCEPT_LANGUAGE, locale.getLanguage());
		else {
			logger.warn(
					"locale is null or neither Arabic or English, fallback to configred rest-client language:{}",
					language);
			headers.add(HttpHeaders.ACCEPT_LANGUAGE, language);
		}

		if (StringUtils.hasLength(username)
				&& StringUtils.hasLength(password)) {
			String userAuthHeader = username.concat(COLON).concat(password);
			try {
				userAuthHeader = AUTH_HEADER_BASIC.concat(new String(
						Base64Utils.encode(userAuthHeader.getBytes(ENCODING))));
			} catch (UnsupportedEncodingException e) {
				logger.warn("Could not encode userAuthHeader {}",
						userAuthHeader);
			}
			headers.add(USER_AUTH_HEADER, userAuthHeader);
			logger.trace("Setting {} header to: {}", USER_AUTH_HEADER,
					userAuthHeader);
		}

		
		
		if(logger.isDebugEnabled()){
			Map<String, String> keys = headers.toSingleValueMap();
			for(Map.Entry<String, String> entry: keys.entrySet() ){
				logger.trace("{}, value: {}", entry.getKey(), entry.getValue());
			}
		}
		logger.debug("buildHeaders done, total headers: {}", headers.size());
		return headers;
	}
}
