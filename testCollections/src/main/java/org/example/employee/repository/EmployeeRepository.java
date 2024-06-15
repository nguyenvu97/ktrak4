package org.example.employee.repository;

import org.example.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeDTO> checkData();
    List<EmployeeDTO> findAll(Number limit , Number offSet, String sortBy );
    String delete(Integer employeeId);

    EmployeeDTO update (EmployeeDTO employeeDTO,Integer employeeID);

    List<EmployeeDTO>findByDepartmentId(Integer departmentId);
}
