package org.example.objectMapper.impl;

import org.example.dto.EmployeeDTO;
import org.example.employee.Employee;

public interface EmployeeMapperImpl {
    EmployeeDTO entityDto(Employee employee);
}
