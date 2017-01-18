package com.packt.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.packt.webstore.service.CustomerService;

@Controller
public class CustomerController {

	// Use 'service layer' to interact with the 'presentation layer', instead of
	// by using 'persistence layer' to interact with the 'presentation layer'
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/customers")
	public String list(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());

		return "customers";
	}
}