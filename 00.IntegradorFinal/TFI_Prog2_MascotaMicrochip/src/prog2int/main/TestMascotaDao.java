/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.main;

import prog2int.dao.MascotaDao;
import prog2int.dao.MascotaDaoJdbc;
import prog2int.entities.Mascota;

/**
 *
 * @author vallett
 */
public class TestMascotaDao {

    public static void main(String[] args) {
        MascotaDao dao = new MascotaDaoJdbc();

        try {
            System.out.println("---- LEER TODAS LAS MASCOTAS ----");
            for (Mascota m : dao.leerTodos()) {
                System.out.println(m);
            }

            System.out.println("\n---- LEER POR ID (1) ----");
            Mascota m1 = dao.leerPorId(1L);
            System.out.println(m1);

            System.out.println("\n---- BUSCAR POR DUEÑO (\"Marianela\") ----");
            for (Mascota m : dao.buscarPorDuenio("Marianela")) {
                System.out.println(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}