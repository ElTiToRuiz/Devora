package src.utils;

import src.db.Database;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


public class InitData {
    public static void setup(){
        new InitData().cargarCuros();
    }

    private void cargarCuros() {
        if(Database.obtenerCursos().size() > 9) return;
        String cursosFile = "src/utils/data/devoracursos.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(cursosFile))) {
            String line;
            reader.readLine();  // Leer y omitir la primera línea del encabezado

            while ((line = reader.readLine()) != null) {
                // Dividir la línea en columnas usando la coma como delimitador
                String[] values = line.split(";");
                System.out.println(values);
                Database.crearCurso(
                        values[0], // Titulo
                        values[1],  // Descripcion
                        Integer.parseInt(values[2]),  // Duración
                        Double.parseDouble(values[3]),  // Precio
                        Integer.parseInt(values[4]),  // Clases
                        Integer.parseInt(values[5]),  // Id Instructor
                        values[6],  // Idioma
                        values[7], // Imagen path
                        Double.parseDouble(values[8]),
                        values[9]
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

