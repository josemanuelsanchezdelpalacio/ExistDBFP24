package DatosXMLaBD;

import conexiones.ConexionMySQL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarFamilias {

    public static void insertar() {
        try {
            // Crear la conexión a la base de datos MySQL
            Connection con = ConexionMySQL.conectar("FP24MJO");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/familiasFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("familia");

            // Creación de los objetos de la entidad EntityEntity
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();

                try {
                    String query = "INSERT IGNORE INTO FAMILY (FamilyCode, FamilyName) VALUES (?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, codigo);
                    pstmt.setString(2, nombre);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error en la operación de la base de datos");
                }
            }
            System.out.println("Datos de FAMILY subidos");
            // Cerrar la conexión a la base de datos
            con.close();

        } catch (ParserConfigurationException | IOException | SAXException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la operación de la base de datos");
        }
    }
}