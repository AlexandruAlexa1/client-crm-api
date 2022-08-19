package com.aa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aa.model.Customer;
import com.aa.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> listCustomers = service.listAll();
		
		model.addAttribute("listCustomers", listCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/new")
	public String addCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		
		return "customer-form";
	}
	
	@PostMapping("/save")
	public String saveCustomer(Customer customer) {
		service.save(customer);	
		
		return "redirect:/customers/list";
	}
	
	@GetMapping("/edit")
	public String editCustomer(@RequestParam("customerId") Integer id, Model model) {
		Customer customer = service.get(id);
		
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") Integer id) {
		service.delete(id);
		
		return "redirect:/customers/list";
	}
}











