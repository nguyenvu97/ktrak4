package org.example.employee;

import org.example.department.Department;
import org.example.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface EmployeeService {
    String add(Employee employee);
    Map<String,Map<String,List<EmployeeDTO>>> getAll(Integer pageNo, Integer pageSize, String sortBy);
    String delete(Integer employeeId);

    EmployeeDTO update (EmployeeDTO employeeDTO,Integer employeeID);
}
