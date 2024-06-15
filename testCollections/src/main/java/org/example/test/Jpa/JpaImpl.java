package org.example.test.Jpa;

import lombok.RequiredArgsConstructor;
import org.example.dbConnet.ConnectionPool;
import org.example.test.impl.Test;
import org.springframework.stereotype.Repository;


@Repository
public class JpaImpl extends Jpa<Test,Integer> {
    public JpaImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public String add(Test test) {
        return super.add(test);
    }
}
