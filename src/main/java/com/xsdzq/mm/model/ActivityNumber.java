package com.xsdzq.mm.model;

public class ActivityNumber {
	private int prizeNumber;
	private int ticketNumber;
	private String token;

	public int getPrizeNumber() {
		return prizeNumber;
	}

	public void setPrizeNumber(int prizeNumber) {
		this.prizeNumber = prizeNumber;
	}

	public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "ActivityNumber [prizeNumber=" + prizeNumber + ", ticketNumber=" + ticketNumber + ", token=" + token
				+ "]";
	}

}
