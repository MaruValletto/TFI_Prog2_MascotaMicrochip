/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package prog2int.dao;

import prog2int.entities.Microchip;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public interface MicrochipDao extends GenericDao<Microchip> {

    // Búsqueda por campo relevante (código único)
    Microchip buscarPorCodigo(String codigo) throws SQLException;

    Microchip buscarPorCodigo(String codigo, Connection connection) throws SQLException;
    
     // Asociar un microchip a una mascota dentro de una transacción
    Microchip crearParaMascota(Microchip microchip, Long mascotaId, Connection connection) throws SQLException;

    // Regla 1→1: buscar si una mascota ya tiene microchip
    Microchip buscarPorMascotaId(Long mascotaId) throws SQLException;

    Microchip buscarPorMascotaId(Long mascotaId, Connection connection) throws SQLException;
}