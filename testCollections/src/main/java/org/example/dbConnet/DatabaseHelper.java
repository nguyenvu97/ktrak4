package org.example.dbConnet;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {
    public static final String DB_URL = "jdbc:postgresql://localhost:5430/Annotation";
    public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "Vu123456";
    public static Connection connnection;

    public static synchronized Connection getConnection(){
        if(connnection == null){
            init();
        }
        return connnection;
    }

    public static void init(){
        // return connection to db
        Connection newConnection = null;
        try {
            // before have connection  ,  driver class
            Class.forName(DRIVER_CLASS_NAME);
            newConnection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            if(newConnection != null){
                System.out.println("Connection successfull ! " );
            }else {
                System.err.println("Connection failed ! " );
            }
        }catch (Exception ex){
            System.err.println("Exception : "+ex.getMessage());
        }
        connnection = newConnection;
    }
}

