package com.xsdzq.mm.model;

public class VoteModel {

	private String empId;

	private String ticketNumber;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	@Override
	public String toString() {
		return "VoteModel [empId=" + empId + ", ticketNumber=" + ticketNumber + "]";
	}

}
