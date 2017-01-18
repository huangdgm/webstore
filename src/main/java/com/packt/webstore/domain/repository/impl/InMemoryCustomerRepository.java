package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	private List<Customer> listOfCustomers = new ArrayList<Customer>();

	public InMemoryCustomerRepository() {
		Customer name1 = new Customer(1, "name1", "address1");

		name1.setNoOfOrderMade(2);

		Customer name2 = new Customer(2, "name2", "address2");

		name2.setNoOfOrderMade(5);

		Customer name3 = new Customer(3, "name3", "address3");

		name3.setNoOfOrderMade(1);

		listOfCustomers.add(name1);
		listOfCustomers.add(name2);
		listOfCustomers.add(name3);
	}

	public List<Customer> getAllCustomers() {
		return listOfCustomers;
	}

}
