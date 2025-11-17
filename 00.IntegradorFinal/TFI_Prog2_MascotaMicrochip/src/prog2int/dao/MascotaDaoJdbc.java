/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.dao;

import prog2int.config.DatabaseConnection;
import prog2int.entities.Mascota;
import prog2int.entities.Microchip;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vallett
 */
public class MascotaDaoJdbc implements MascotaDao {

    // ===== SQL base =====
    private static final String INSERT_SQL =
            "INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
            "UPDATE mascota SET nombre = ?, especie = ?, raza = ?, fecha_nacimiento = ?, duenio = ? " +
            "WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE mascota SET eliminado = TRUE WHERE id = ?";

    // SELECT con LEFT JOIN a microchip (para traer el código si existe)
    private static final String SELECT_BASE =
            "SELECT m.id AS m_id, m.eliminado AS m_eliminado, m.nombre, m.especie, m.raza, " +
            "       m.fecha_nacimiento, m.duenio, " +
            "       mc.id AS mc_id, mc.eliminado AS mc_eliminado, mc.codigo, mc.fecha_implantacion, " +
            "       mc.veterinaria, mc.observaciones " +
            "FROM mascota m " +
            "LEFT JOIN microchip mc ON mc.mascota_id = m.id AND mc.eliminado = FALSE " +
            "WHERE m.eliminado = FALSE ";

    private static final String SELECT_BY_ID_SQL =
            SELECT_BASE + "AND m.id = ?";

    private static final String SELECT_ALL_SQL =
            SELECT_BASE;

    private static final String SELECT_BY_NOMBRE_SQL =
            SELECT_BASE + "AND UPPER(m.nombre) LIKE ?";

    private static final String SELECT_BY_DUENIO_SQL =
            SELECT_BASE + "AND UPPER(m.duenio) LIKE ?";

    // ===== Métodos con conexión propia =====

    @Override
    public Mascota crear(Mascota entidad) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return crear(entidad, conn);
        }
    }

    @Override
    public Mascota leerPorId(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerPorId(id, conn);
        }
    }

    @Override
    public List<Mascota> leerTodos() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public void actualizar(Mascota entidad) throws SQLException {
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
    public List<Mascota> buscarPorNombre(String nombre) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return buscarPorNombre(nombre, conn);
        }
    }

    @Override
    public List<Mascota> buscarPorDuenio(String duenio) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return buscarPorDuenio(duenio, conn);
        }
    }

    // ===== Métodos con Connection externa (para transacciones) =====

    @Override
    public Mascota crear(Mascota entidad, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, entidad.isEliminado());
            ps.setString(2, entidad.getNombre());
            ps.setString(3, entidad.getEspecie());
            ps.setString(4, entidad.getRaza());
            if (entidad.getFechaNacimiento() != null) {
                ps.setDate(5, Date.valueOf(entidad.getFechaNacimiento()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, entidad.getDuenio());

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
    public Mascota leerPorId(Long id, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMascota(rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<Mascota> leerTodos(Connection connection) throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mascotas.add(mapRowToMascota(rs));
            }
        }
        return mascotas;
    }

    @Override
    public void actualizar(Mascota entidad, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getEspecie());
            ps.setString(3, entidad.getRaza());
            if (entidad.getFechaNacimiento() != null) {
                ps.setDate(4, Date.valueOf(entidad.getFechaNacimiento()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, entidad.getDuenio());
            ps.setLong(6, entidad.getId());
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
    public List<Mascota> buscarPorNombre(String nombre, Connection connection) throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_NOMBRE_SQL)) {
            ps.setString(1, "%" + nombre.toUpperCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mascotas.add(mapRowToMascota(rs));
                }
            }
        }
        return mascotas;
    }

    @Override
    public List<Mascota> buscarPorDuenio(String duenio, Connection connection) throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_DUENIO_SQL)) {
            ps.setString(1, "%" + duenio.toUpperCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mascotas.add(mapRowToMascota(rs));
                }
            }
        }
        return mascotas;
    }

    // ===== Mapeo ResultSet -> Mascota (+ Microchip si existe) =====

    private Mascota mapRowToMascota(ResultSet rs) throws SQLException {
        Long id = rs.getLong("m_id");
        boolean eliminado = rs.getBoolean("m_eliminado");
        String nombre = rs.getString("nombre");
        String especie = rs.getString("especie");
        String raza = rs.getString("raza");
        Date fechaSql = rs.getDate("fecha_nacimiento");
        LocalDate fechaNacimiento = (fechaSql != null) ? fechaSql.toLocalDate() : null;
        String duenio = rs.getString("duenio");

        // Armar Microchip solo si hay uno asociado
        Long mcId = rs.getLong("mc_id");
        Microchip microchip = null;
        if (!rs.wasNull()) {
            boolean mcEliminado = rs.getBoolean("mc_eliminado");
            String codigo = rs.getString("codigo");
            Date fechaImplSql = rs.getDate("fecha_implantacion");
            LocalDate fechaImplantacion = (fechaImplSql != null) ? fechaImplSql.toLocalDate() : null;
            String veterinaria = rs.getString("veterinaria");
            String observaciones = rs.getString("observaciones");

            microchip = new Microchip(mcId, mcEliminado, codigo, fechaImplantacion, veterinaria, observaciones);
        }

        Mascota mascota = new Mascota(id, eliminado, nombre, especie, raza, fechaNacimiento, duenio, microchip);
        return mascota;
    }
}
