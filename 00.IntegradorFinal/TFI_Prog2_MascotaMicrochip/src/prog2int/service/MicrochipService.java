/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.service;
import prog2int.dao.MicrochipDao;
import prog2int.dao.MicrochipDaoJdbc;
import prog2int.entities.Microchip;

import java.util.List;


/**
 *
 * @author vallett
 */
public class MicrochipService implements GenericService<Microchip> {

    private final MicrochipDao microchipDao;

    public MicrochipService() {
        this.microchipDao = new MicrochipDaoJdbc();
    }

    @Override
public Microchip insertar(Microchip microchip) throws Exception {
    throw new UnsupportedOperationException(
            "El alta de microchip se realiza a través de MascotaService.registrarMascotaConMicrochip().");
}

    @Override
    public Microchip actualizar(Microchip microchip) throws Exception {
        if (microchip.getId() == null) {
            throw new IllegalArgumentException("El microchip debe tener ID para ser actualizado.");
        }
        validarMicrochip(microchip);
        microchipDao.actualizar(microchip);
        return microchip;
    }

    @Override
    public void eliminar(Long id) throws Exception {
        microchipDao.eliminarLogico(id);
    }

    @Override
    public Microchip getById(Long id) throws Exception {
        return microchipDao.leerPorId(id);
    }

    @Override
    public List<Microchip> getAll() throws Exception {
        return microchipDao.leerTodos();
    }

    private void validarMicrochip(Microchip microchip) {
        if (microchip == null) {
            throw new IllegalArgumentException("El microchip no puede ser nulo.");
        }
        if (microchip.getCodigo() == null || microchip.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del microchip es obligatorio.");
        }
    }
}