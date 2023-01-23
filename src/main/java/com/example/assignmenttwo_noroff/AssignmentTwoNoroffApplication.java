package com.example.assignmenttwo_noroff;

import CRUD.API;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentTwoNoroffApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentTwoNoroffApplication.class, args);

		API example = new API();
		example.test();
		System.out.println(example.getAllCustomers(1));

	}

}
