package org.ost.contacts.controllers;


import org.ost.contacts.services.ContactService;
import org.ost.entity.contacts.vo.ContactUpdateVo;
import org.ost.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	@Autowired
	private ContactService contactService;

	@RequestMapping(value = "contacts", method = RequestMethod.POST)
	public void createContact(@RequestBody ContactUpdateVo contactUpdateVo) {
		this.contactService.createCustomer(contactUpdateVo);
	}
	
	@RequestMapping(value = "contacts/list", method = RequestMethod.GET)
	public void ContactList(@RequestBody Customer customer) {
		//this.contactService.createCustomer(customer);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void contact(@RequestBody Customer customer) {
		//this.contactService.createCustomer(customer);
	}
	
	@RequestMapping(value = "contacts/{id}", method = RequestMethod.POST)
	public void updateContact(@RequestBody ContactUpdateVo contactUpdateVo) {
		this.contactService.updateContact(contactUpdateVo);
	}
}
