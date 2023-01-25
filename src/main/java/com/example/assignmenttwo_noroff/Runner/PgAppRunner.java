package com.example.assignmenttwo_noroff.Runner;

import com.example.assignmenttwo_noroff.CRUD.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PgAppRunner implements ApplicationRunner {

    //private final Crud _crud;

    private final Crud _crud;

    @Autowired
    public PgAppRunner(Crud crud) {
        this._crud = crud;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("from runner");
        _crud.test();
    }
}
