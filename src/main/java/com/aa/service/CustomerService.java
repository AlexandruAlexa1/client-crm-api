package com.aa.service;

import java.util.List;

import com.aa.model.Customer;

public interface CustomerService {

	public List<Customer> listAll();
	
	public Customer get(Integer id);
	
	public void save(Customer customer);
	
	public void delete(Integer id);
}
