package com.psl.assessment.model;

public enum Status {
	ACTIVE("Active"),
	INACTIVE("Inactive");
	
	private String status;
	
	private Status(String status)
	{
		this.status = status;
	}
	
	public static Status parseFromString(String status)
	{
		return Status.valueOf(status.toUpperCase());
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	@Override
	public String toString()
	{
		return this.status;
	}
}