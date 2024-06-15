package org.example.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SERIAL)
    public Integer id;
    @NonNull
    public String departmentName;
    @NonNull
    public int sum_Employee;
}
