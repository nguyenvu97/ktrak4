package org.example.test.Jpa;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface TestExecutor<T,ID>{
    String add(T t);
    T findById(ID id );

}
