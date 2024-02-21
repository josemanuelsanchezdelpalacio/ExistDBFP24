package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSGBD {
    private static final String URL = "jdbc:mysql://54.37.220.4:3306/";
    private static final String USUARIO = "MNJMSOG24";
    private static final String CLAVE = "G-UMg2[fdFk18o";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexión OK.");
        } catch (SQLException e) {
            System.err.println("Error en la conexión");
            e.printStackTrace();
        }
        return conexion;
    }

}