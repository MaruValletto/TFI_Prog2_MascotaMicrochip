/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package prog2int.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface GenericDao<T> {

    // Métodos que manejan su propia conexión
    T crear(T entidad) throws SQLException;

    T leerPorId(Long id) throws SQLException;

    List<T> leerTodos() throws SQLException;

    void actualizar(T entidad) throws SQLException;

    void eliminarLogico(Long id) throws SQLException;

    // Métodos que usan una Connection externa (para transacciones)
    T crear(T entidad, Connection connection) throws SQLException;

    T leerPorId(Long id, Connection connection) throws SQLException;

    List<T> leerTodos(Connection connection) throws SQLException;

    void actualizar(T entidad, Connection connection) throws SQLException;

    void eliminarLogico(Long id, Connection connection) throws SQLException;
}