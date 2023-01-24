package com.example.assignmenttwo_noroff;

import CRUD.API;
import models.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentTwoNoroffApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentTwoNoroffApplication.class, args);

		API example = new API();
		example.test();
		//System.out.println(example.getAllCustomers());
		//System.out.println(example.getCustomerByName("Leonie"));
		//System.out.println(example.getCustomerById(1));
		//System.out.println(example.getPageOfCustomers(10,15));
		//System.out.println(example.addCustomer("Odd", "Kveseth", "Norway", "0586", "95482043", "oddm@gmail.com"));
		//System.out.println(example.getCustomerByName("Odd"));

		//example.editCustomerLastName(1, "Rodrigez");
		System.out.println(example.mostCustomersCountry());
	}

}
