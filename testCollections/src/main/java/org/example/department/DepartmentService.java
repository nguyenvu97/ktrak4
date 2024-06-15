package org.example.department;

import org.example.position.Position;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    String add(List<Department> department);

    Optional<Department> findById(Integer id);


}
