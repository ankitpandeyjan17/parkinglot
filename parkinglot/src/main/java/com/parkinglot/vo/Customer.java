package com.parkinglot.vo;

public class Customer {
	  private String name;
	  private String phone;
	  private boolean seniorCititzen;

	  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isSeniorCititzen() {
		return seniorCititzen;
	}
	public void setSeniorCititzen(boolean seniorCititzen) {
		this.seniorCititzen = seniorCititzen;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", phone=" + phone + ", seniorCititzen=" + seniorCititzen + "]";
	}
	
	
	
	
	}
