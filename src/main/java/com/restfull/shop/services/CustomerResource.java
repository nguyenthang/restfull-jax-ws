package com.restfull.shop.services;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import com.restfull.shop.domain.Customer;

public interface CustomerResource {
	
	public Response createCustomer(InputStream is);
	
	public Response updateCustomer(int customerId, InputStream is);
	
	public Response getCustomer(int customerId);
	
	public Customer readCustomer(InputStream is);
}