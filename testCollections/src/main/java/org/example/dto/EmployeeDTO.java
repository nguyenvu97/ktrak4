package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.NonNull;

import java.sql.Date;

@Setter
@Getter

public class EmployeeDTO {
//    public int id;

    public String employeeName;
    public String departmentName;
    public Date birth;
    public int insurance;
    public int departmentId;
    public int positionId;
    public String positionName;
    public String CCCD;
    public String Status;
    public double basic_Salary;
    public double salary_Net;
}
