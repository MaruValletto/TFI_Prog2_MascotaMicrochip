/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.service;
import prog2int.config.DatabaseConnection;
import prog2int.dao.MascotaDao;
import prog2int.dao.MascotaDaoJdbc;
import prog2int.dao.MicrochipDao;
import prog2int.dao.MicrochipDaoJdbc;
import prog2int.entities.Mascota;
import prog2int.entities.Microchip;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author vallett
 */
public class MascotaService implements GenericService<Mascota> {

    private final MascotaDao mascotaDao;
    private final MicrochipDao microchipDao;

    public MascotaService() {
        this.mascotaDao = new MascotaDaoJdbc();
        this.microchipDao = new MicrochipDaoJdbc();
    }

    // ========== CRUD "simple" de Mascota (sin tocar microchip) ==========

    @Override
    public Mascota insertar(Mascota mascota) throws Exception {
        validarMascota(mascota);
        return mascotaDao.crear(mascota);
    }

    @Override
    public Mascota actualizar(Mascota mascota) throws Exception {
        if (mascota.getId() == null) {
            throw new IllegalArgumentException("La mascota debe tener ID para ser actualizada.");
        }
        validarMascota(mascota);
        mascotaDao.actualizar(mascota);
        return mascota;
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la mascota no puede ser nulo.");
        }
        mascotaDao.eliminarLogico(id);
        // Si quisieras, acá podrías también hacer baja lógica del microchip asociado
        // usando microchipDao.buscarPorMascotaId(id) y luego eliminarLogico(...)
    }

    @Override
    public Mascota getById(Long id) throws Exception {
        return mascotaDao.leerPorId(id);
    }

    @Override
    public List<Mascota> getAll() throws Exception {
        return mascotaDao.leerTodos();
    }

    // ========== Búsquedas específicas ==========

    public List<Mascota> buscarPorNombre(String nombre) throws Exception {
        return mascotaDao.buscarPorNombre(nombre);
    }

    public List<Mascota> buscarPorDuenio(String duenio) throws Exception {
        return mascotaDao.buscarPorDuenio(duenio);
    }

    // ========== Operación TRANSACCIONAL: Mascota + Microchip ==========

    /**
     * Registra una nueva mascota y su microchip asociado en una única transacción.
     * Si algo falla en cualquiera de los dos inserts, se hace rollback.
     */
    public Mascota registrarMascotaConMicrochip(Mascota mascota, Microchip microchip) throws Exception {
        validarMascota(mascota);
        validarMicrochip(microchip);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // INICIO TRANSACCIÓN

            // 1) Insertar Mascota
            mascota = mascotaDao.crear(mascota, conn);

            // 2) Regla 1→1: verificar que la mascota NO tenga ya un microchip
            Microchip existente = microchipDao.buscarPorMascotaId(mascota.getId(), conn);
            if (existente != null) {
                throw new IllegalStateException("La mascota ya tiene un microchip asignado.");
            }

            // 3) Insertar Microchip asociado
            microchip.setEliminado(false);
            microchip = microchipDao.crearParaMascota(microchip, mascota.getId(), conn);

            // 4) Asociar en memoria
            mascota.setMicrochip(microchip);

            conn.commit(); // FIN EXITOSO
            return mascota;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // ROLLBACK ante cualquier error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // ========== Validaciones ==========

    private void validarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula.");
        }
        if (esVacio(mascota.getNombre())) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }
        if (esVacio(mascota.getEspecie())) {
            throw new IllegalArgumentException("La especie de la mascota es obligatoria.");
        }
        if (esVacio(mascota.getDuenio())) {
            throw new IllegalArgumentException("El nombre del dueño es obligatorio.");
        }

        // Validación simple de fecha: no futura
        LocalDate fn = mascota.getFechaNacimiento();
        if (fn != null && fn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }
    }

    private void validarMicrochip(Microchip microchip) {
        if (microchip == null) {
            throw new IllegalArgumentException("El microchip no puede ser nulo.");
        }
        if (esVacio(microchip.getCodigo())) {
            throw new IllegalArgumentException("El código del microchip es obligatorio.");
        }
        // Podrías agregar validaciones extra del formato del código, etc.
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}