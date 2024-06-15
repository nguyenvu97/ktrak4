package org.example.annotation.Impl;



import org.example.annotation.Entity;
import org.example.annotation.GeneratedValue;
import org.example.annotation.Id;
import org.example.annotation.NonNull;
import org.example.dbConnet.ConnectionPool;

import java.lang.reflect.Field;
import java.sql.*;


public class JpaExecutorImpl<T> {
    private Class<T> clazz;
    private String className;
    public String tableName;
    Field[] fields;

    public JpaExecutorImpl(Class<T> clazz) throws SQLException {
        this.clazz = clazz;
        this.className = clazz.getSimpleName();
        this.tableName = clazz.getAnnotation(Entity.class) != null ?
                clazz.getAnnotation(Entity.class).tableName()
                : className;
        this.fields = clazz.getDeclaredFields();
        System.err.println(this.className);
        System.err.println(this.tableName);
        System.err.println(this.fields);
    }

    public void crate(Class<T> clazz) throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection con = connectionPool.getConnection();
        if (con == null) {
            System.err.println("Connection is null. Cannot proceed with table creation.");
            return;
        }
        this.tableName = clazz.getAnnotation(Entity.class) != null ?
                clazz.getAnnotation(Entity.class).tableName()
                : clazz.getSimpleName(); // Sử dụng tên lớp nếu không có Annotation @Entity

        String sql = "CREATE TABLE " + tableName + " (";
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String fieldType = getSQLType(field.getType().getSimpleName());
            sql += fieldName + " " + fieldType;

            if (field.isAnnotationPresent(GeneratedValue.class)) {
                sql = sql.replace("INT", "SERIAL");

                System.out.println(sql);
            }
            if (field.isAnnotationPresent(Id.class)) {
                sql += " PRIMARY KEY";
            }
            if (field .isAnnotationPresent(NonNull.class)){
                sql += " " + "not null";
            }
            sql += ", ";
        }

        sql = sql.substring(0, sql.length() - 2);
        sql += ")";

        System.out.println("SQL query: " + sql);

        if (!checkTable(tableName, con)) {
            Statement statement = con.createStatement();
            statement.execute(sql);
            System.out.println("Table created successfully.");
        } else {
            System.out.println("Table already exists.");
        }
    }

    public Boolean checkTable(String tableName, Connection con) throws SQLException {
        DatabaseMetaData metaData = con.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.next();
    }

    public String getSQLType(String javaType) {
        switch (javaType) {
            case "String":
                return "VARCHAR(255)";
            case "int":
            case "Integer":
                return "INT";
            case "double":
            case "Double":
                return "DOUBLE PRECISION";
            case "float":
            case "Float":
                return "FLOAT";
            case "long":
            case "Long":
                return "BIGINT";
            case "date":
            case "DATE":
                return "DATE";
            case  "LocalDateTime":
                return "timestamp";
            default:
                return "VARCHAR(255)";
        }
    }


}




