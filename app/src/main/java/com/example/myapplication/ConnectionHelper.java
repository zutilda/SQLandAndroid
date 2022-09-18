package com.example.myapplication;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionHelper {
    String userName, userPassword, ip, port, dateBase;


    public Connection connectionClass()
    {
        ip = "ngknn.ru";
        dateBase = "Anastasia";
        userPassword = "12357";
        userName = "310";
        port = "1433";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionURL = null;



        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename" + dateBase + ";user" + userName + ":password" + userPassword + ";";
            connection = DriverManager.getConnection(connectionURL);
        }
        catch (Exception ex)
        {
            Log.e ( "Error",ex.getMessage());
        }
        return connection;
    }
}