package com.nfespy.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.swing.text.html.HTMLDocument;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaptchaServiceTest {

	private static final String IMAGE_64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAALQAAAAyCAYAAAD1JPH3AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAABbcSURBVHhe7Z1ZrGZTFsevmOehxFQo8xREzDNlnmcxxRwxjyVmMXUoJYYSootGUVJEKJ0gWhCNhNAP1BOeiqQT3jx6PH1/2/2fXnfdtc/Z53znaunuf7JS3z1nn73XXuu/1157n/19NTZjxoylY2NjVU622WabZZLZs2f/48wzz/w7cumll/5twYIFCyVLliz5c1VV9/74449z+Zt/+fuXX375kz7nhDKqR2UXLVr0F1/ujyz0f/78+YvmzZu3WH1BPv/88yei8v8LUuL7oWVs1qxZ/4yIPIRsscUW1c4775z+PfTQQ5Mcc8wx1T333DNJrr766uqQQw5JMnfu3Orxxx9Pz996663V+++/n/5G3n777erjjz8eWe67775qfEBO0QORnpGss846U/o4pOywww6T2pNODz30UPXqq69Wr7322rjPph/jREwyKt56662kfw7Lli2rvv3224m/RsfXX39djY3/C7Or5557rhqPvNWWW25ZrbjiiqHBJdxfe+21p93B/5dYsDuEP+WUUxJhXnzxxTRQ21BC1F9//TVxARmV1OiVI3Qi30R/IP4QoJ6a0B4//PBD9cADD1QHHnhgtf76608yaCTrrrtutfvuu1ennnpqdffdd9fRcPHixdXll19eRxsJkdpGIysHH3xwtdVWW1Urr7xy2FZX8XWPp0+pfvqm6xdddNEUHSVER/UHYaZ4880302cizHvvvVe3RfRvA89BGD2DnVX37bffXs9W5513XtKNWU5l24Qor1kQvb/44ouJVn8jEQIg608//ZQ+W9Af9VtlPaKBQVnbFqD+XB2JfBM6P/PMMxNXR0eW0BHeeeed6pJLLqn23HPPoui8xhprVJtvvnl18sknV7fccktymDrpOz8Uvvvuu+qDDz6Y+Gt64CMPUY2UiNSgNKo99thjtZ2uuOKKVIdIHdVBmzieFIwyIh2EJ6Wzdo9k3333rW644YZUD6Rtip5tePfddydFVTugqbcU6hN9B/RLn/uiE6EjPPXUU3VnILA+l8i2226b8mQ6RmfIqZqAo3MOF9rytiHAoHn66adbdWmCjVBPPvlkUWRsggYE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";

	public static final String URL = "http://exchange.telstra.com.au/wp-content/uploads/2014/09/CAPTCHA.jpg";

	@Autowired
	private CaptchaService captchaService;

	@Test
	public void solveByBase64() throws IOException {
		final String solveByBase64 = captchaService.solve(IMAGE_64);
		assertEquals("fm4zbh", solveByBase64);
	}

	@Test
	public void solveByUrl() throws IOException {
		final String solveByBase64 = captchaService.solve(URL);
		assertEquals("capton", solveByBase64);
	}

	@Test
	public void downloadImage() throws IOException {
		final String image = captchaService.downloadImage(URL);
		assertEquals("ABC", image);
	}

}