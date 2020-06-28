package com.itheima.sms;

public class SmsBean {

	private String address;
	private String date;
	private String read;
	private String type;
	private String body;
	
	public SmsBean(String address, String date, String read, String type,
			String body) {
		super();
		this.address = address;
		this.date = date;
		this.read = read;
		this.type = type;
		this.body = body;
	}

	@Override
	public String toString() {
		return "SmsBean [address=" + address + ", date=" + date + ", read="
				+ read + ", type=" + type + ", body=" + body + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
