package org.example.test.impl;

import org.example.test.Jpa.TestExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;





public interface TestRepository  extends TestExecutor<Test,Integer> {
}
