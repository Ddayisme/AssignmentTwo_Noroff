package com.example.assignmenttwo_noroff.Repositories;

import com.example.assignmenttwo_noroff.models.Customer;
import com.example.assignmenttwo_noroff.models.CustomerCountry;
import com.example.assignmenttwo_noroff.models.CustomerGenre;
import com.example.assignmenttwo_noroff.models.CustomerSpender;

import java.util.List;

/**
 * Our Customer interface as a repository which inherits from CRUDRepository.
 *
 *
 */

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {

    Customer getCustomerByName(String name);
    List<Customer> getPageOfCustomers(int limit, int offset);
    CustomerSpender findHighestSpendingCustomer();
    CustomerCountry mostCustomersCountry();
    List<CustomerGenre> findHighestGenreForACustomer(int id);
    void test();

}
