package org.example.test.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.annotation.*;

import java.time.LocalDateTime;

@Entity(tableName = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SERIAL)
    public Integer id;
    @NonNull
    public String testName;
    @NonNull
    public String categoryTest;
    @NonNull
    public LocalDateTime startTest;
    @NonNull
    public LocalDateTime endTest;

}
