package com.restfull.shop.resource;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.restfull.shop.domain.Customer;


@Path("customers")
public class CustomerResource {

	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	
	private AtomicInteger idCounter = new AtomicInteger();
	
	@POST
	@Consumes
	public Response createCustomer(InputStream is){
		Customer customer = readCustomer(is);
		customer.setCustomerId(idCounter.incrementAndGet());
		customerDB.put(customer.getCustomerId(), customer);
		System.out.println("Create Customer" + customer.getCustomerId());
		return javax.ws.rs.core.Response.created(URI.create("/customers/" + customer.getCustomerId())).build();
		
	}

	private Customer readCustomer(InputStream is) {
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			
			Customer cust = new Customer();
			
			if(root.getAttribute("customerId") != null && (!root.getAttribute("customerId").equals(""))){
				cust.setCustomerId(Integer.valueOf(root.getAttribute("customerId")));						
			}
			
			NodeList nodes = root.getChildNodes();
			
			for(int i = 0 ; i < nodes.getLength() ; i++){
				Element elements = (Element)nodes.item(i);
				
				if(elements.getTagName().equals("first-name")){
					cust.setFirstName(elements.getTextContent());
				}
				if(elements.getTagName().equals("last-name")){
					cust.setFirstName(elements.getTextContent());
				}
				if(elements.getTagName().equals("street")){
					cust.setFirstName(elements.getTextContent());
				}
				
				if(elements.getTagName().equals("city")){
					cust.setFirstName(elements.getTextContent());
				}
			}
			
		}catch(Exception e){
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
		return null;
	}
}
