/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package prog2int.service;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface GenericService<T> {

    T insertar(T entidad) throws Exception;

    T actualizar(T entidad) throws Exception;

    void eliminar(Long id) throws Exception;

    T getById(Long id) throws Exception;

    List<T> getAll() throws Exception;
}