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

		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(
																				url, 
																				HttpMethod.GET,
																				new HttpEntity<>(createHeaders("aa", "0000")),
																				new ParameterizedTypeReference<List<Customer>>() {}
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
