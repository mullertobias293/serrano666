/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sistema {

    private BufferedWriter writer;

    // Constructor donde inicializamos el BufferedWriter
    public Sistema() {
        try {
            // Asegúrate de que el archivo logs.txt se abra en modo append (agregar al final)
            File archivo = new File("logs.txt");
            

            // Si el archivo no existe, se crea
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Abrir el archivo en modo "append" para agregar datos al final
            writer = new BufferedWriter(new FileWriter(archivo, true));

        } catch (IOException e) {
            System.out.println("Error al abrir el archivo de logs: " + e.getMessage());
        }
    }

    // Método para registrar el log
    public void registrarLog(String mensaje) {
        try {
            // Obtener el timestamp actual
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Escribir el log en el archivo
            writer.write(timestamp + " - " + mensaje);
            writer.newLine();  // Nueva línea después del log

            // Imprimir en consola para verificar
            System.out.println("Log registrado: " + timestamp + " - " + mensaje);
        } catch (IOException e) {
            System.out.println("Error al registrar el log: " + e.getMessage());
        }
    }

    // Método que procesa (asegura que todo se haya escrito) y cierra el BufferedWriter
    public void procesarLogs() {
        try {
            if (writer != null) {
                writer.flush();  // Vacía el buffer
                writer.close();  // Cierra el BufferedWriter al finalizar
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo de logs: " + e.getMessage());
        }
    }
}