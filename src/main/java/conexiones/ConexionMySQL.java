package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static final String URL = "jdbc:mysql://54.37.220.4:3306/";
    private static final String USUARIO = "MNJMSOG24";
    private static final String CLAVE = "G-UMg2[fdFk18o";

    public static Connection conectar(String nombreBD) {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL + nombreBD, USUARIO, CLAVE);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
}

