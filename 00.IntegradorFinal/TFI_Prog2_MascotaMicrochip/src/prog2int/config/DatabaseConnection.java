/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vallett
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/vetdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";    
    private static final String PASSWORD = "1234";  

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}