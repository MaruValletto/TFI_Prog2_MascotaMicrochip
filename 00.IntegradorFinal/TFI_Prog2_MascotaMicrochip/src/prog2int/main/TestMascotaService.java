/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package prog2int.main;

import prog2int.entities.Mascota;
import prog2int.entities.Microchip;
import prog2int.service.MascotaService;

import java.time.LocalDate;

/**
 *
 * @author vallett
 */
public class TestMascotaService {

    public static void main(String[] args) {
        MascotaService service = new MascotaService();

        try {
            Mascota m = new Mascota(
                    "Rocky",
                    "Perro",
                    "Mestizo",
                    LocalDate.of(2020, 5, 10),
                    "Dueño Prueba",
                    null
            );

            Microchip chip = new Microchip(
                    "CHIP-TEST-ROLL", 
                    LocalDate.now(),
                    "Vet Prueba",
                    "Alta por Service"
            );

            Mascota creada = service.registrarMascotaConMicrochip(m, chip);
            System.out.println("Mascota creada con microchip: " + creada);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}