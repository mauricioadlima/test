package com.nfespy.site;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NacionalTest {

	@Autowired
	private Nacional nacional;

	@Test
	public void mustGetImage() throws IOException {
		nacional.getNfe("35170560627429000135550020011701121473193288");
	}
}