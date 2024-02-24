package DatosXMLaBD;

import conexiones.ConexionMySQL;
import entities.EntityEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertarCentros {

    public static void insertar() {
        PreparedStatement ps;
        ResultSet rs;

        // Parseo del archivo XML
        try (Connection con = ConexionMySQL.conectar("FP24MJO")){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/centrosFinal.xml");
            Document document = builder.parse(p.toFile());
            document.getDocumentElement().normalize();

            // Obtención de la lista de nodos <entidad>
            NodeList nodeList = document.getElementsByTagName("entidad");

            // Creación de los objetos de la entidad EntityEntity
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                // Setear valores en el objeto EntityEntity
                String codigo = element.getElementsByTagName("codigo").item(0).getTextContent().isEmpty() ? String.valueOf(-(i + 1)) : element.getElementsByTagName("codigo").item(0).getTextContent();
                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                String web = element.getElementsByTagName("web").item(0).getTextContent();
                String correo = element.getElementsByTagName("correoElectronico").item(0).getTextContent();

                // Insertar en la base de datos
                ps = con.prepareStatement("INSERT IGNORE INTO ENTITY (EntityName, EntityCode, Web, Email) VALUES (?, ?, ?, ?)");
                ps.setString(1, nombre);
                ps.setString(2, codigo);
                ps.setString(3, web);
                ps.setString(4, correo);
                ps.executeUpdate();
            }
            System.out.println("Datos de ENTITY subidos");
        } catch (ParserConfigurationException | IOException | SAXException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la operación de la base de datos");
        }
    }
}