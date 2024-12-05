package javaapplication1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://b7bqlyp9wflopvdwklxn-mysql.services.clever-cloud.com:3306/b7bqlyp9wflopvdwklxn";
    private static final String USUARIO = "u3kvhd5surotasij";
    private static final String CONTRASENA = "98XeROAFkzzXYvQKhEor";

    private static Connection conexion = null;

    // Método para obtener la conexión
    public static Connection obtenerConexion() {
        if (conexion == null) {
            try {
                // Cargar el driver de MySQL (aunque desde JDBC 4.0 ya no es necesario explícitamente)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión exitosa con la base de datos.");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}