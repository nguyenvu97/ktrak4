package org.example.employee.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.department.Department;
import org.example.department.DepartmentService;
import org.example.dto.EmployeeDTO;
import org.example.employee.Employee;
import org.example.employee.Status;
import org.example.employee.repository.EmployeeAccessService;
import org.example.employee.EmployeeService;
import org.example.exception.Not_Found;
import org.example.position.Position;
import org.example.position.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeAccessService employeeAccessService;
    private final PositionService positionService;
    private final DepartmentService departmentService;


    @Override
    public String add(Employee employee) {
        if (!rules(employee.getCCCD())){
            throw new Not_Found("CCCD error" + employee.getCCCD());
        }
        Position position = positionService.findById(employee.getPositionID()).orElseThrow(() -> new Not_Found("not found" + employee.positionID));
        List<EmployeeDTO> employeeDTOS = employeeAccessService.checkData();


        long sumEmployeeOn = employeeDTOS.stream()
                .filter(employeeDTO -> employeeDTO.getDepartmentId() == employee.getDepartmentId() &&
                        Status.ON.name().equals(employeeDTO.getStatus()))
                .count();


        long checkCCCD = employeeDTOS.stream().filter(employeeDTO ->
                String.valueOf(employeeDTO.getCCCD()).
                equals(String.valueOf(employee.getCCCD()))
        ).count();
        if (checkCCCD >= 1){
            throw new Not_Found( "employee of CCCD in database " + employee.getCCCD());

        }
        Department department = checkSumEmployee(employee.getDepartmentId());
        if (department.getSum_Employee() <= sumEmployeeOn ){
            throw new Not_Found( "Department full employee");
        }

        if (position.getPositionName().equals("ManagerAdmin")) {
            Long managerAdminCount = countEmployee(employeeDTOS,"ManagerAdmin",Status.ON,true, 0);
            if (managerAdminCount >= 1 ) {
                return "Data already exists for ManagerAdmin position";
            }
            employeeAccessService.add(employee);
            return "create ManagerAdmin OK";
        }else if (position.getPositionName().equals("Manager")) {
            Long managerCount = countEmployee(employeeDTOS,"Manager",Status.ON,true , 0);
            if (managerCount >= 2) {
                return "Data already exists for Manager position";
            }
            employeeAccessService.add(employee);
            return "create Manager OK";
        }else {
            employeeAccessService.add(employee);
            return "add Employee OK";
        }

    }


    private Department checkSumEmployee(Integer departmentId){
       Department department = departmentService.findById(departmentId).orElseThrow(()-> new Not_Found("not department" + departmentId));
        return department;
    }
    private boolean rules(String cccd) {
        try{
            if (cccd.length() >= 12 && cccd.length() < 13) {
                 Long.parseLong(cccd);
                 return true;
            }
        }catch (NumberFormatException e){
            throw new RuntimeException(e.getMessage());
        }
        return false;
    }


    //Check Position manager and managerAdmin
    private Long countEmployee(List<EmployeeDTO> employeeDTOS,String positionName , Status status , boolean choose , Integer positionId ){
        if (choose ){
            long manager = employeeDTOS.stream()
                    .filter(employeeDTO -> positionName.equals(employeeDTO.getPositionName())&& employeeDTO.getStatus().equals(String.valueOf(status)))
                    .count();
            return manager;
        }else {
            long manager = employeeDTOS.stream()
                    .filter(employeeDTO -> employeeDTO.getPositionId() == positionId  && employeeDTO.getStatus().equals(String.valueOf(status)))
                    .count();
            return manager;
        }

    }


    @Override
    public Map<String,Map<String,List<EmployeeDTO>>> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        int offset = pageNo * pageSize;
        List<EmployeeDTO> employeeDTOS = employeeAccessService.findAll(pageSize,offset, sortBy);

        Map<String, Map<String,List<EmployeeDTO>>> listMap= employeeDTOS.stream().collect(Collectors.groupingBy(EmployeeDTO::getDepartmentName,Collectors.groupingBy(EmployeeDTO::getPositionName)));

        return listMap;
    }

    @Override
    public String delete(Integer employeeId) {
        if (employeeId <= 0){
            return null;
        }
        employeeAccessService.delete(employeeId);
        return "delete Ok";
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO, Integer employeeId) {
        Objects.requireNonNull(employeeDTO, "infomation not Ok");
        if (employeeId <0){
            return null;
        }


        Position position = positionService.findById(employeeDTO.getPositionId()).orElseThrow(() -> new Not_Found("not found" + employeeDTO.getPositionId()));
        List<EmployeeDTO> employeeDTOS1 = employeeAccessService.checkData();
        List<EmployeeDTO> employeeDTOS = employeeDTOS1.stream().filter(
                employeeDTO1 -> employeeDTO.getDepartmentId() == employeeDTO.getDepartmentId()
        ).collect(Collectors.toList());

        Long CCCD = employeeDTOS1.stream().filter(employeeDTO1 -> employeeDTO1.getCCCD().equals(employeeDTO.getCCCD())).count();
        if (CCCD >= 1){
            throw new Not_Found("CCCD hava in database");
        }
        if (position.getPositionName().equals("ManagerAdmin")) {
            Long managerAdminCount = countEmployee(employeeDTOS, "ManagerAdmin",Status.ON,false,employeeDTO.getPositionId());
            if (managerAdminCount >= 1) {
                throw new Not_Found(" Now position have a ManagerAdmin ");
            }
            employeeAccessService.update(employeeDTO, employeeId);
            return employeeDTO;
        } else if (position.getPositionName().equals("Manager")) {
            Long managerCount = countEmployee(employeeDTOS, "Manager",Status.ON,false,employeeDTO.getPositionId());
            if (managerCount >= 2) {
                throw new Not_Found(" Now position have a Manager");
            }
            employeeAccessService.update(employeeDTO, employeeId);
            return employeeDTO;
        } else {
            employeeAccessService.update(employeeDTO,employeeId);
            return employeeDTO;
        }
    }
}
