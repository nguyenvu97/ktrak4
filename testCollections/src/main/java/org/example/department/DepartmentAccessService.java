package org.example.department;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Impl.JpaExecutor;
import org.example.dbConnet.ConnectionPool;
import org.example.position.Position;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public  class DepartmentAccessService implements JpaExecutor<Department> {

    public  final ConnectionPool connectionPool;




    @Override
    public Optional<Department> findById(Number id) {

            String sql = """
                select * from department where id = ?
                """;

            Department department = null;
            try(Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, (Integer) id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String departmentname = resultSet.getString("departmentName");
                    int sum_Employee = resultSet.getInt("sum_Employee");
                    department = new Department((Integer) id,departmentname,sum_Employee);
                }
                return Optional.ofNullable(department);
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }

        }


    @Override
    public String add(Department department) {
         String sql = "INSERT INTO department (departmentname, sum_employee) VALUES (?,?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getDepartmentName());
            preparedStatement.setInt(2, department.getSum_Employee());
            preparedStatement.execute();
            return "ok";
        } catch (SQLException e) {
            e.printStackTrace();
            return "not ok";
        }
    }


}
