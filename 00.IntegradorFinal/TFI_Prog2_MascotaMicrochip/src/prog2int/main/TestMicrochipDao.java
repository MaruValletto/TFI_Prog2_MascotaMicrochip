/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.main;

import prog2int.dao.MicrochipDao;
import prog2int.dao.MicrochipDaoJdbc;
import prog2int.entities.Microchip;

import java.time.LocalDate;

/**
 *
 * @author vallett
 */
public class TestMicrochipDao {

    public static void main(String[] args) {
        MicrochipDao dao = new MicrochipDaoJdbc();

        try {
            // 1) PROBAR LECTURA TODOS
            System.out.println("---- LEER TODOS ----");
            for (Microchip m : dao.leerTodos()) {
                System.out.println(m);
            }

            // 2) PROBAR LECTURA POR ID
            System.out.println("\n---- LEER POR ID (ID=1) ----");
            Microchip mc = dao.leerPorId(1L);
            System.out.println(mc);

            // 3) PROBAR BUSCAR POR CÓDIGO
            System.out.println("\n---- BUSCAR POR CÓDIGO ----");
            Microchip buscado = dao.buscarPorCodigo("CHIP-001-AR-2020");
            System.out.println(buscado);

            // 4) PROBAR INSERTAR UNO NUEVO (SIN mascota_id todavía)
            System.out.println("\n---- INSERTAR NUEVO MICROCHIP ----");
            Microchip nuevo = new Microchip(
                    "CHIP-TEST-001",
                    LocalDate.now(),
                    "Vet de prueba",
                    "Prueba DAO"
            );

            Microchip insertado = dao.crear(nuevo);  // NO funciona si mascota_id es NOT NULL
            System.out.println("Insertado: " + insertado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}