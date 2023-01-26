package com.example.assignmenttwo_noroff.models;

/**
 * Model for our Customer data as a record
 *
 * @param customer_id
 * @param first_name
 * @param last_name
 * @param country
 * @param postal_code
 * @param phone
 * @param email
 */
public record Customer(int customer_id, String first_name, String last_name, String country, String postal_code, String phone, String email) {

}
