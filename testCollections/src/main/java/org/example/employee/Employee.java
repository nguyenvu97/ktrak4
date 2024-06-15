package org.example.employee;


import com.fasterxml.jackson.databind.annotation.EnumNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SERIAL)
    public Integer id;
    @NonNull
    public String employeeName;
    @NonNull
    public Date birth;
    @NonNull
    public double basic_Salary;
    @NonNull
    public double salary_Net;
    @NonNull
    public int insurance;
    @NonNull
    public int departmentId;
    @NonNull
    public int  positionID;
    @NonNull
    public String CCCD;
    @NonNull
    public String employeeStatus;


}
