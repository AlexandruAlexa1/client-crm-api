package com.aa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aa.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private RestTemplate restTemplate;
	
	String url;
	
	@Autowired
	public CustomerServiceImpl(@Value("${api-1}") String theUrl, RestTemplateBuilder RTB ) {
		this.restTemplate = RTB.basicAuthentication("alexandru", "0000").build();
		this.url = theUrl;
	}

	@Override
	public List<Customer> getCustomers() {
		
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
																				url,
																				HttpMethod.GET,
																				null,
																				new ParameterizedTypeReference<List<Customer>> () {}
																			  );
		
		List<Customer> customers = responseEntity.getBody();
		
		return customers;
	}

	@Override
	public Customer getCustomer(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub

	}

}
