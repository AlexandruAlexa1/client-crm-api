package com.aa.service;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aa.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${api-1}")
	String url;

	public HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@Override
	public List<Customer> getCustomers() {

		// make REST call
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
																				url, 
																				HttpMethod.GET,
																				new HttpEntity<Customer>(createHeaders("aa", "0000")),
																				new ParameterizedTypeReference<List<Customer>>() {}
																		  	 );
		// get the list of customers from response
		List<Customer> customers = responseEntity.getBody();
		
		return customers;
	}

	@Override
	public Customer getCustomer(int id) {
		
		// make REST call
		ResponseEntity<Customer> responseEntity = restTemplate.exchange(
																		url + "/" + id, 
																		HttpMethod.GET,
																		new HttpEntity<Customer>(createHeaders("aa", "0000")),
																		Customer.class
																		);
		Customer customer = responseEntity.getBody();
		
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		int id = customer.getId();
		
		// make REST call
		if (id == 0) {
			// add customer
			restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(customer, createHeaders("alexa", "0000")), Customer.class);
		} else {
			// update customer
			restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Customer>(customer, createHeaders("alexa", "0000")), Customer.class);
		}

	}

	@Override
	public void deleteCustomer(int id) {
		
		// make REST call
		restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, new HttpEntity<>(createHeaders("alexandru", "0000")), Void.class);
	}

}
