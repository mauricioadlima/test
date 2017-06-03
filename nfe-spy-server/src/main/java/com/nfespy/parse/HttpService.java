package com.nfespy.parse;

import java.io.IOException;

import com.nfespy.model.CanonicNfe;

public interface HttpService {

	String getImage() throws IOException;

	CanonicNfe post(String captcha, String key);
}
