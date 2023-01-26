package com.example.assignmenttwo_noroff.Repositories;

import java.util.List;

/**
 * Our interface for the basic CRUD requirments with generic objects T, and generic datatype U,
 * for our primary key Id.
 *
 * @param <T>
 * @param <U>
 */
public interface CRUDRepository<T, U> {

    List<T> getAllCustomers();
    T getCustomerById(U id);
    int insertCustomer(T object);
    int updateCustomer(T object);
    int delete(T object);
    int deleteById(U id);

}
