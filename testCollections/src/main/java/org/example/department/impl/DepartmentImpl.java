package org.example.department.impl;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Impl.JpaExecutor;
import org.example.department.Department;
import org.example.department.DepartmentService;
import org.example.position.Position;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentImpl implements DepartmentService {

    private final JpaExecutor<Department> departmentJpaExecutor;
    @Override
    public String add(List<Department> department) {
        for (Department department1 : department) {
            departmentJpaExecutor.add(department1);
        }
        return "Department added successfully";

    }

    @Override
    public Optional<Department> findById(Integer id) {
        if (id <0){
            return Optional.empty();
        }
        return departmentJpaExecutor.findById(id);
    }

}
