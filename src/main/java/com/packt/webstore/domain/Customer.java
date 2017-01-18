package com.packt.webstore.domain;

public class Customer {
	int customerId;
	String name;
	String address;
	int noOfOrderMade;

	public Customer(int customerId, String name, String address) {
		this.customerId = customerId;
		this.name = name;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNoOfOrderMade() {
		return noOfOrderMade;
	}

	public void setNoOfOrderMade(int noOfOrderMade) {
		this.noOfOrderMade = noOfOrderMade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (customerId != other.customerId)
			return false;
		return true;
	}
}
