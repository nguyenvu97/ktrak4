package org.example.employee.repository;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Impl.JpaExecutor;
import org.example.dbConnet.ConnectionPool;
import org.example.dto.EmployeeDTO;
import org.example.employee.Employee;
import org.example.employee.Status;
import org.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public  class EmployeeAccessService implements JpaExecutor<Employee>, EmployeeRepository {

    private final ConnectionPool connectionPool;

    @Override
    public List<EmployeeDTO> findAll(Number limit, Number offSet, String sortBy) {
        String sql = "SELECT e.*, p.positionname, d.departmentname " +
                "FROM employee AS e " +
                "LEFT JOIN position AS p ON e.positionid = p.id " +
                "LEFT JOIN department AS d ON e.departmentid = d.id " +
                "where e.employeeStatus = ?" +
                "ORDER BY " + sortBy + " LIMIT ? OFFSET ?";

        List<EmployeeDTO> employees = new ArrayList<>();
            try (Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, String.valueOf(Status.ON));
                preparedStatement.setInt(2, (Integer) limit);
                preparedStatement.setInt(3, (Integer) offSet);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setPositionName(resultSet.getString("positionName"));
//                    employeeDTO.setId(resultSet.getInt("id"));
                    employeeDTO.setEmployeeName(resultSet.getString("employeeName"));
                    employeeDTO.setBirth(resultSet.getDate("birth"));
                    employeeDTO.setInsurance(resultSet.getInt("insurance"));
                    employeeDTO.setDepartmentId(resultSet.getInt("departmentId"));
                    employeeDTO.setCCCD(resultSet.getString("CCCD"));
                    employeeDTO.setStatus(resultSet.getString("employeeStatus"));
                    employeeDTO.setDepartmentName(resultSet.getString("departmentName"));
                    employees.add(employeeDTO);
                }
                System.out.println(employees);
                return employees;

            }catch (SQLException e){
                e.getMessage();
                return null;
            }

    }

    @Override
    public String delete(Integer employeeId) {
        var sql = "UPDATE employee set employeestatus = ? where id =?";
        try(Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1,String.valueOf(Status.OFF));
            preparedStatement.setInt(2,employeeId);
             preparedStatement.executeUpdate();
            return "delete Ok";

        }catch (SQLException e){
            e.getStackTrace();
            return null;
        }

    }




    @Override
    public Optional<Employee> findById(Number id) {
        return Optional.empty();
    }
    @Override
    public String add(Employee employee){

        String sql = """
                insert into employee(employeename, birth, basic_salary, salary_net, insurance, departmentid, positionid, cccd, employeestatus) values (?,?,?,?,?,?,?,?,?)
                """;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setDate(2, employee.getBirth());
            preparedStatement.setDouble(3, employee.getBasic_Salary());
            preparedStatement.setDouble(4, employee.getSalary_Net());
            preparedStatement.setInt(5,employee.getInsurance());
            preparedStatement.setInt(6,employee.getDepartmentId());
            preparedStatement.setInt(7,employee.getPositionID());
            preparedStatement.setString(8, employee.getCCCD());
            preparedStatement.setString(9, String.valueOf(Status.ON));
            preparedStatement.executeUpdate();
            return "Ok";
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<EmployeeDTO> checkData(){
        String sql = """
                select p.positionname,e.* from employee  as e LEFT JOIN position p ON e.positionId = p.id  
                """;
        List<EmployeeDTO> employees = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

//            preparedStatement.setInt(1, (Integer) departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setPositionName(resultSet.getString("positionName"));
//                employeeDTO.setId(resultSet.getInt("id"));
                employeeDTO.setEmployeeName(resultSet.getString("employeeName"));
                employeeDTO.setBirth(resultSet.getDate("birth"));
                employeeDTO.setInsurance(resultSet.getInt("insurance"));
                employeeDTO.setDepartmentId(resultSet.getInt("departmentId"));
                employeeDTO.setCCCD(resultSet.getString("CCCD"));
                employeeDTO.setStatus(resultSet.getString("employeeStatus"));
                employees.add(employeeDTO);
            }
            System.out.println(employees);
            return employees;
        }catch (SQLException e){
            e.printStackTrace();;
           return null;
        }
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO,Integer employeeId) {

        String sql = "UPDATE employee SET  basic_Salary = ?, " +
                "salary_Net = ?, insurance = ?, departmentId = ?, positionId = ?, " +
                "CCCD = ? " +
                "WHERE id = ?";
        System.out.println(sql);
        EmployeeDTO employeeDTO1 = null;
        try(Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setDouble(1, employeeDTO.getBasic_Salary());
            preparedStatement.setDouble(2, employeeDTO.getSalary_Net());
            preparedStatement.setInt(3,employeeDTO.getInsurance());
            preparedStatement.setInt(4,employeeDTO.getDepartmentId());
            preparedStatement.setInt(5,employeeDTO.getPositionId());
            preparedStatement.setString(6, employeeDTO.getCCCD());
            preparedStatement.setInt(7,employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeDTO1 = new EmployeeDTO();
                employeeDTO.setPositionName(resultSet.getString("positionName"));
                employeeDTO.setEmployeeName(resultSet.getString("employeeName"));
                employeeDTO.setBirth(resultSet.getDate("birth"));
                employeeDTO.setInsurance(resultSet.getInt("insurance"));
                employeeDTO.setDepartmentId(resultSet.getInt("departmentId"));
                employeeDTO.setCCCD(resultSet.getString("CCCD"));
                employeeDTO.setStatus(resultSet.getString("employeeStatus"));

            }
            return employeeDTO1;

        }catch (SQLException e){
            e.getStackTrace();
            return  null;
        }

    }

    @Override
    public List<EmployeeDTO> findByDepartmentId(Integer departmentId) {
        var sql = "select * from employee where departmentid =? and employeestatus = ?";
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,departmentId);
            preparedStatement.setString(2,String.valueOf(Status.ON));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setEmployeeName(resultSet.getString("employeeName"));
                employeeDTO.setBirth(resultSet.getDate("birth"));
                employeeDTO.setInsurance(resultSet.getInt("insurance"));
                employeeDTO.setDepartmentId(resultSet.getInt("departmentId"));
                employeeDTO.setPositionId(resultSet.getInt("positionId"));
                employeeDTO.setCCCD(resultSet.getString("CCCD"));
                employeeDTO.setStatus(resultSet.getString("employeeStatus"));
                employeeDTOS.add(employeeDTO);
            }
            return employeeDTOS;

        }catch (SQLException e){
            e.getStackTrace();
            return null;
        }

    }


}
