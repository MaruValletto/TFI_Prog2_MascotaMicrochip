/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.entities;
import java.time.LocalDate;

/**
 *
 * @author vallett
 */
public class Mascota extends BaseEntity {

    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private String duenio;
    private Microchip microchip;

    public Mascota() {
    }

    public Mascota(Long id, boolean eliminado, String nombre, String especie,
                   String raza, LocalDate fechaNacimiento, String duenio, Microchip microchip) {
        super(id, eliminado);
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.duenio = duenio;
        this.microchip = microchip;
    }

    public Mascota(String nombre, String especie, String raza,
                   LocalDate fechaNacimiento, String duenio, Microchip microchip) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.duenio = duenio;
        this.microchip = microchip;
        this.eliminado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDuenio() {
        return duenio;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    public Microchip getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Microchip microchip) {
        this.microchip = microchip;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", duenio='" + duenio + '\'' +
                ", microchip=" + (microchip != null ? microchip.getCodigo() : "SIN_MICROCHIP") +
                '}';
    }
}