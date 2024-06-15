package org.example.test.Jpa;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Entity;
import org.example.dbConnet.ConnectionPool;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor

public abstract class Jpa<T,ID> implements TestExecutor<T,ID>  {
    private Class<T> clazz;
    private String className;
    public String tableName;
    Field[] fields;

    private final ConnectionPool connectionPool;


    @Override
    public String add(T t) {
        this.tableName = clazz.getAnnotation(Entity.class) != null ?
                clazz.getAnnotation(Entity.class).tableName()
                : clazz.getSimpleName();
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        this.fields = clazz.getDeclaredFields();
        for (int i = 0 ; i<= fields.length ;i ++){
            sqlBuilder.append(fields[i] .getName());
            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(") VALUES (");
        for (int i = 0;i <= fields.length; i++){
            sqlBuilder.append("?");
            if ((i < fields.length -1)){
                sqlBuilder.append(",");
            }
        }
        sqlBuilder.append(")");
        var sql = sqlBuilder.toString();
        System.out.println( sql);
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            for (int i = 0 ;i <= fields.length ; i++){
                preparedStatement.setObject(i + 1, fields);
            }
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.getStackTrace();
            return sql;
        }
        return sql;
    }


    @Override
    public T findById(ID id) {
        return null;
    }
}
