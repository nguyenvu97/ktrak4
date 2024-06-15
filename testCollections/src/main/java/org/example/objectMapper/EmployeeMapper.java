package org.example.objectMapper;

import org.example.dto.EmployeeDTO;
import org.example.employee.Employee;
import org.example.objectMapper.impl.EmployeeMapperImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements EmployeeMapperImpl {
    @Override
    public EmployeeDTO entityDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }
}
