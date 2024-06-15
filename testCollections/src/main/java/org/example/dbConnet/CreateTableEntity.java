package org.example.dbConnet;



import org.example.annotation.Impl.JpaExecutorImpl;
import org.example.department.Department;
import org.example.employee.Employee;
import org.example.position.Position;
import org.example.test.impl.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class CreateTableEntity {
    @Bean
    public void create(){
        try {
            JpaExecutorImpl<Department> jpaExecutor = new JpaExecutorImpl<>(Department.class);
            jpaExecutor.crate(Department.class);
            JpaExecutorImpl<Employee> jpaExecutor1 = new JpaExecutorImpl<>(Employee.class);
            jpaExecutor1.crate(Employee.class);
            JpaExecutorImpl<Position> jpaExecutor2 = new JpaExecutorImpl<>(Position.class);
            jpaExecutor2.crate(Position.class);
            JpaExecutorImpl<Test> jpaExecutor3 = new JpaExecutorImpl<>(Test.class);
            jpaExecutor3.crate(Test.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
