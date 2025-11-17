/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package prog2int.dao;

import prog2int.entities.Mascota;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface MascotaDao extends GenericDao<Mascota> {

    // Búsqueda por campo relevante (nombre o dueño)
    List<Mascota> buscarPorNombre(String nombre) throws SQLException;

    List<Mascota> buscarPorNombre(String nombre, Connection connection) throws SQLException;

    List<Mascota> buscarPorDuenio(String duenio) throws SQLException;

    List<Mascota> buscarPorDuenio(String duenio, Connection connection) throws SQLException;
}