package com.example.assignmenttwo_noroff.Runner;

import com.example.assignmenttwo_noroff.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PgAppRunner implements ApplicationRunner {

    //private final Crud _crud;

    private final CustomerRepository _customerRepository;

    @Autowired
    public PgAppRunner(CustomerRepository customerRepository) {
        this._customerRepository = customerRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
