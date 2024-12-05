/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.time.LocalDateTime;

public class Log {
    private String mensaje;
    private LocalDateTime fecha;

    public Log(String mensaje) {
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " + mensaje;
    }
}
