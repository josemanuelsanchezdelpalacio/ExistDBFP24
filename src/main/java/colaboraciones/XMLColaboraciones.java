package colaboraciones;


import conexiones.ConexionMySQL;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class XMLColaboraciones {

    public static void procesarXML() {
        try {
            Connection con = ConexionMySQL.conectar("FP24MJO");

            Path p = Path.of("target/proyectosFinal.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Proyectos.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Proyectos proyectos = (Proyectos) jaxbUnmarshaller.unmarshal(p.toFile());

            ArrayList<Proyecto> listaProyectos = proyectos.getProyectos();
            for (Proyecto proyecto : listaProyectos) {
                String tituloProyecto = proyecto.getTituloProyecto();
                String coordinacion = proyecto.getCoordinacion();

                //consultas a la base de datos
                int idProyecto = obtenerIdProyecto(tituloProyecto, coordinacion, con);

                //insertar los ID en la base de datos
                if (idProyecto > 0) {
                    insertarEnBaseDeDatos(idProyecto, con);
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al procesar el XML");
        }
    }

    private static int obtenerIdProyecto(String tituloProyecto, String coordinacion, Connection con) throws SQLException {
        int idProyecto = 0;
        try {
            String query = "SELECT Id FROM PROJECT WHERE Title = ? AND Coordinacion = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, tituloProyecto);
                pstmt.setString(2, coordinacion);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idProyecto = rs.getInt("Id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID del proyecto");
        }
        return idProyecto;
    }

    private static int obtenerIdUsuario(String idUser, Connection con) throws SQLException {
        int userId = 0;
        try {
            String query = "SELECT Id FROM USERS WHERE Login = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, idUser);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("Id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del usuario");
        }
        return userId;
    }

    private static void insertarEnBaseDeDatos(int idProyecto, Connection con) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/colaboracionesFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("Proyecto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String idUser = element.getElementsByTagName("IdUser").item(0).getTextContent();
                boolean isManager = Boolean.parseBoolean(element.getElementsByTagName("IsManager").item(0).getTextContent());

                try {
                    //consulta para obtener el ID del usuario
                    int userId = obtenerIdUsuario(idUser, con);

                    if (userId > 0) {
                        String query = "INSERT IGNORE INTO COLLABORATION (IdProject, IdUser, IsManager) VALUES (?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(query)) {
                            pstmt.setInt(1, idProyecto);
                            pstmt.setInt(2, userId);
                            pstmt.setBoolean(3, isManager);

                            pstmt.executeUpdate();
                        }
                    } else {
                        System.out.println("No se pudo obtener el ID del usuario necesario");
                    }

                } catch (SQLException e) {
                    System.out.println("Error al insertar en la base de datos");
                }
            }
            System.out.println("Datos insertados en la tabla de colaboraciones");

        } catch (Exception e) {
            System.out.println("Error al procesar el XML");
        }
    }
}