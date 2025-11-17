/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.dao;

import prog2int.config.DatabaseConnection;
import prog2int.entities.Microchip;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vallett
 */
public class MicrochipDaoJdbc implements MicrochipDao {

    private static final String INSERT_SQL =
            "INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones, mascota_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM microchip WHERE id = ? AND eliminado = FALSE";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM microchip WHERE eliminado = FALSE";

    private static final String UPDATE_SQL =
            "UPDATE microchip SET codigo = ?, fecha_implantacion = ?, veterinaria = ?, observaciones = ? " +
            "WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE microchip SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_CODIGO_SQL =
            "SELECT * FROM microchip WHERE codigo = ? AND eliminado = FALSE";

    private static final String SELECT_BY_MASCOTA_ID_SQL =
            "SELECT * FROM microchip WHERE mascota_id = ? AND eliminado = FALSE";

    // ---- Métodos con conexión propia ----

    @Override
    public Microchip crear(Microchip entidad) throws SQLException {
        // NO usar este método genérico para inserts, porque necesitamos el id de la mascota.
        // Se deja explícito para evitar usos incorrectos.
        throw new UnsupportedOperationException(
                "Use crearParaMascota(microchip, mascotaId, connection) para respetar la relación 1→1."
        );
    }

    @Override
    public Microchip leerPorId(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerPorId(id, conn);
        }
    }

    @Override
    public List<Microchip> leerTodos() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public void actualizar(Microchip entidad) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(entidad, conn);
        }
    }

    @Override
    public void eliminarLogico(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminarLogico(id, conn);
        }
    }

    @Override
    public Microchip buscarPorCodigo(String codigo) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return buscarPorCodigo(codigo, conn);
        }
    }

    @Override
    public Microchip buscarPorMascotaId(Long mascotaId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return buscarPorMascotaId(mascotaId, conn);
        }
    }

    // ---- Métodos con Connection externa (para transacciones) ----

    /**
     * NO se usa para crear microchips porque no recibe el id de la mascota.
     * Se deja sólo para cumplir con la interfaz genérica.
     */
    @Override
    public Microchip crear(Microchip entidad, Connection connection) throws SQLException {
        throw new UnsupportedOperationException(
                "Use crearParaMascota(microchip, mascotaId, connection) para crear respetando la FK."
        );
    }

    /**
     * Crea un microchip asociado a una mascota concreta (respeta la FK y el 1→1).
     */
    @Override
    public Microchip crearParaMascota(Microchip entidad, Long mascotaId, Connection connection) throws SQLException {
        if (mascotaId == null) {
            throw new IllegalArgumentException("El id de la mascota no puede ser nulo al crear un microchip.");
        }

        try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, entidad.isEliminado());
            ps.setString(2, entidad.getCodigo());
            if (entidad.getFechaImplantacion() != null) {
                ps.setDate(3, Date.valueOf(entidad.getFechaImplantacion()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, entidad.getVeterinaria());
            ps.setString(5, entidad.getObservaciones());
            ps.setLong(6, mascotaId);   // ahora sí, mascota_id real

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entidad.setId(rs.getLong(1));
                }
            }
            return entidad;
        }
    }

    @Override
    public Microchip leerPorId(Long id, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMicrochip(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Microchip> leerTodos(Connection connection) throws SQLException {
        List<Microchip> microchips = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                microchips.add(mapRowToMicrochip(rs));
            }
        }
        return microchips;
    }

    @Override
    public void actualizar(Microchip entidad, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, entidad.getCodigo());
            if (entidad.getFechaImplantacion() != null) {
                ps.setDate(2, Date.valueOf(entidad.getFechaImplantacion()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, entidad.getVeterinaria());
            ps.setString(4, entidad.getObservaciones());
            ps.setLong(5, entidad.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_LOGICO_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Microchip buscarPorCodigo(String codigo, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODIGO_SQL)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMicrochip(rs);
                }
                return null;
            }
        }
    }

    @Override
    public Microchip buscarPorMascotaId(Long mascotaId, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_MASCOTA_ID_SQL)) {
            ps.setLong(1, mascotaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMicrochip(rs);
                }
                return null;
            }
        }
    }

    // ---- Método privado de mapeo ResultSet -> Microchip ----

    private Microchip mapRowToMicrochip(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        boolean eliminado = rs.getBoolean("eliminado");
        String codigo = rs.getString("codigo");
        Date fechaSql = rs.getDate("fecha_implantacion");
        LocalDate fechaImplantacion = (fechaSql != null) ? fechaSql.toLocalDate() : null;
        String veterinaria = rs.getString("veterinaria");
        String observaciones = rs.getString("observaciones");

        Microchip microchip = new Microchip(id, eliminado, codigo, fechaImplantacion, veterinaria, observaciones);
        return microchip;
    }
}