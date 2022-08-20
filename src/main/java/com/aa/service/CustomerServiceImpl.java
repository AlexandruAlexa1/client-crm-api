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

	@Value("${UrlAPI}")
	String url;

	public HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
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
	public List<Customer> listAll() {
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
														url, 
														HttpMethod.GET,
														new HttpEntity<Customer>(createHeaders("user1", "0000")),
														new ParameterizedTypeReference<List<Customer>>() {}
												  	 );
		
		List<Customer> listCustomers = responseEntity.getBody();
		
		return listCustomers;
	}

	@Override
	public Customer get(Integer id) {
		ResponseEntity<Customer> responseEntity = restTemplate.exchange(
													url + "/" + id, 
													HttpMethod.GET,
													new HttpEntity<Customer>(createHeaders("user1", "0000")),
													Customer.class
												);
		
		return responseEntity.getBody();
	}

	@Override
	public void save(Customer customer) {
		boolean isNewMode = customer.getId() == 0;
		
		if (isNewMode) {
			restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Customer>(customer, createHeaders("user3", "0000")), Customer.class);
		} else {
			restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<Customer>(customer, createHeaders("user3", "0000")), Customer.class);
		}
	}

	@Override
	public void delete(Integer id) {
		restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, new HttpEntity<>(createHeaders("user3", "0000")), Void.class);
	}

}
