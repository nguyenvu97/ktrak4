package org.example.position;

import lombok.RequiredArgsConstructor;

import org.example.annotation.Impl.JpaExecutor;
import org.example.dbConnet.ConnectionPool;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



@Repository
@RequiredArgsConstructor
public class PositionAccessService implements JpaExecutor<Position>{

    private final ConnectionPool connectionPool;


    @Override
    public String add(Position positions) {
        String sql = "INSERT INTO position (positionname) VALUES (?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, positions.getPositionName());
            preparedStatement.execute();
            return "ok";
        } catch (SQLException e) {
            e.printStackTrace();
            return "not ok";
        }
    }



    @Override
    public Optional<Position> findById(Number id) {
        String sql = """
                select * from position where id = ?
                """;

        Position position = null;
        try(Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, (Integer) id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String positionName = resultSet.getString("positionName");
                position = new Position((Integer) id,positionName);
            }
            return Optional.ofNullable(position);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}