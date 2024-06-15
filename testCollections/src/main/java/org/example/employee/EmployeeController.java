package org.example.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.dto.EmployeeDTO;
import org.example.exception.Not_Found;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")

@RequiredArgsConstructor
public class EmployeeController {

    public final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> add (@RequestBody Employee employee){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.add(employee));
        }catch (Not_Found e){
           return ResponseEntity.ok(e.getMessage());
        }

    }
    @GetMapping
    public ResponseEntity<?> getAll ( @RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll(pageNo,pageSize,sortBy));
        }catch (Not_Found e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete ( @RequestParam Integer employeeId ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.delete(employeeId));
        }catch (Not_Found e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> update (@RequestParam Integer employeeId , @RequestBody EmployeeDTO employeeDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(employeeDTO,employeeId));
        }catch (Not_Found e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
