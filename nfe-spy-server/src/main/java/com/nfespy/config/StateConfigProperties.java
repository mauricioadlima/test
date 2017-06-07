package com.nfespy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("states")
@Component
public class StateConfigProperties {

	public static class State {

		public static final class StateProperty {

			private String type;

			private String value;

			public String getType() {
				return type;
			}

			public void setType(final String type) {
				this.type = type;
			}

			public String getValue() {
				return value;
			}

			public void setValue(final String value) {
				this.value = value;
			}
		}

		private String url;

		private StateProperty button;

		private StateProperty captcha;

		private StateProperty image;

		private StateProperty key;

		public String getUrl() {
			return url;
		}

		public void setUrl(final String url) {
			this.url = url;
		}

		public StateProperty getButton() {
			return button;
		}

		public void setButton(final StateProperty button) {
			this.button = button;
		}

		public StateProperty getCaptcha() {
			return captcha;
		}

		public void setCaptcha(final StateProperty captcha) {
			this.captcha = captcha;
		}

		public StateProperty getImage() {
			return image;
		}

		public void setImage(final StateProperty image) {
			this.image = image;
		}

		public StateProperty getKey() {
			return key;
		}

		public void setKey(final StateProperty key) {
			this.key = key;
		}
	}

	private State nacional;

	private State sp;

	public State getNacional() {
		return nacional;
	}

	public void setNacional(final State nacional) {
		this.nacional = nacional;
	}

	public State getSp() {
		return sp;
	}

	public void setSp(final State sp) {
		this.sp = sp;
	}
}
