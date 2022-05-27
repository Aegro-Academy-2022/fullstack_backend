package com.aegro.exception;

public class CommonExceptionDetails extends ErrorDetails{

	public static final class Builder{
		private String title;
		private int status;
		private long timestamp;
		private String message;
		
		private Builder() {}
		
		public static Builder newBuilder() {
			return new Builder();
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder status(int status) {
			this.status = status;
			return this;
		}
		
		public Builder timestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		
		public CommonExceptionDetails build() {
			CommonExceptionDetails details = new CommonExceptionDetails();
			
			details.setMessage(message);
			details.setTitle(title);
			details.setTimestamp(timestamp);
			details.setStatus(status);
			
			return details;
		}
	}


}
