-- 02_insert_data.sql

USE vetdb;

INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio)
VALUES
    (FALSE, 'Luna', 'Perro', 'Labrador', '2019-05-10', 'Marianela Valletto'),
    (FALSE, 'Milo', 'Gato', 'Siames', '2021-03-02', 'Juan Pérez'),
    (FALSE, 'Kira', 'Perro', 'Caniche', '2020-11-20', 'Laura Gómez');

INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones, mascota_id)
VALUES
    (FALSE, 'CHIP-001-AR-2020', '2020-01-15', 'VetCare Centro', 'Implantación sin complicaciones', 1),
    (FALSE, 'CHIP-002-AR-2021', '2021-04-10', 'Clínica Animalia', 'Control anual al día', 2),
    (FALSE, 'CHIP-003-AR-2022', '2022-01-05', 'Veterinaria Santa Fe', 'Observaciones: mascota nerviosa', 3);
