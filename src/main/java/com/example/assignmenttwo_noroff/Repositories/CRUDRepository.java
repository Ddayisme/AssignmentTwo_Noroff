package com.example.assignmenttwo_noroff.Repositories;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface CRUDRepository<T, U> {

    List<T> getAllCustomers();
    T getCustomerById(U id);
    int insertCustomer(T object);
    int updateCustomerLastName(T object);
    int delete(T object);
    int deleteById(U id);

}
