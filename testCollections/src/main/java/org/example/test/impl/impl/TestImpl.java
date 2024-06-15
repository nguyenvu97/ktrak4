package org.example.test.impl.impl;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Impl.JpaExecutor;
import org.example.test.Jpa.JpaImpl;
import org.example.test.impl.Test;
import org.example.test.impl.TestRepository;
import org.example.test.impl.TestService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestImpl implements TestService {
    private final JpaImpl jpa;

    @Override
    public String add(Test test) {
        return jpa.add(test);
    }
}
