package com.packt.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.packt.webstore.service.OrderService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/P1234/2")
	public String processP1234() {
		orderService.processOrder("P1234", 2);
		
		return "redirect:/products";
	}
	
	@RequestMapping("/order/P1235/2")
	public String processP1235() {
		orderService.processOrder("P1235", 2);
		
		return "redirect:/products";
	}
	
	@RequestMapping("/order/P1236/2")
	public String processP1236() {
		orderService.processOrder("P1236", 2);
		
		return "redirect:/products";
	}
}