package com.newton.moex.access.pojo;

public class data {
	private String ticker;
	private String lastPrice;

	public data(String ticker, String lastPrice) {
		super();
		this.ticker = ticker;
		this.lastPrice = lastPrice;
	}

	public String getTicker() {
		return ticker;
	}

	public String getLastPrice() {
		return lastPrice;
	}

}
