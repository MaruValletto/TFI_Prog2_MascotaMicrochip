/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.main;

import prog2int.config.DatabaseConnection;
import java.sql.Connection;

/**
 *
 * @author vallett
 */
public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión exitosa a MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
