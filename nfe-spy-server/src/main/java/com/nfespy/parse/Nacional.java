package com.nfespy.parse;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nfespy.model.CanonicNfe;

@Component
public class Nacional extends AbstractHttpService {

	private static final String QUERY_TO_IMAGE = "#ctl00_ContentPlaceHolder1_imgCaptcha";

	private static final Logger LOGGER = LoggerFactory.getLogger(Nacional.class);

	@Override
	public String getImage() throws IOException {
		return Jsoup.connect(stateConfig.getNacional())
					.get()
					.select(QUERY_TO_IMAGE)
					.attr("src");
	}

	@Override
	public CanonicNfe post(final String captcha, final String key) {
		return new CanonicNfe();
	}

}
