package org.example.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.Entity;
import org.example.annotation.GeneratedValue;
import org.example.annotation.GenerationType;
import org.example.annotation.Id;
import org.example.employee.Employee;

@Entity(tableName = "position")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.SERIAL)
    private Integer id;
    private String positionName;
}
