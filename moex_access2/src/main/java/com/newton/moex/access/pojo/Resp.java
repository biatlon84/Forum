package com.newton.moex.access.pojo;

public class Resp {
	private data resp;
	private long responseTime;

	public Resp(String ti, String last) {
		super();
		this.resp = new data(ti, last);
		this.responseTime = System.currentTimeMillis() / 1000;
	}

	public data getResp() {
		return resp;
	}

	public long getResponseTime() {
		return responseTime;
	}

}