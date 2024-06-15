package org.example.dbConnet;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static org.example.dbConnet.DatabaseHelper.*;


@Component
public class ConnectionPool {
    public static ConnectionPool instance;
    private List<Connection> connections;

    private static final int POOL_SIZE = 30;

    public static synchronized ConnectionPool getInstance(){
        if(instance == null){
            instance = new ConnectionPool();
        }
        return instance;
    }
    public  synchronized Connection getConnection(){
        if(connections.isEmpty()){
            return null;
        }
        return  connections.remove(connections.size() -1);
    }
    public ConnectionPool() {

        connections = new ArrayList<>();
        for (int  i = 0 ; i < POOL_SIZE ; i ++){
            Connection newConnection = null;
            try {
                // before have connection  ,  driver class
                Class.forName(DRIVER_CLASS_NAME);
                newConnection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
                connections.add(newConnection);
            }catch (Exception ex){
                System.err.println("Exception : "+ex.getMessage());
            }
        }
    }



}
