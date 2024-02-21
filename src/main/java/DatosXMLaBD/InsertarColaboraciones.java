package DatosXMLaBD;

import conexiones.ConexionMySQL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InsertarColaboraciones {
    public static void insertar() {
        try {
            Connection con = ConexionMySQL.conectar("FP24MJO");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/colaboracionesFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("Colaboracion");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String idProject = element.getElementsByTagName("IdProject").item(0).getTextContent();
                String idUser = element.getElementsByTagName("IdUser").item(0).getTextContent();
                String idFamily = element.getElementsByTagName("IdFamily").item(0).getTextContent();
                boolean isManager = Boolean.parseBoolean(element.getElementsByTagName("IsManager").item(0).getTextContent());

                try {
                    String query = "INSERT IGNORE INTO COLLABORATIONS (IdProject, IdUser, IdFamily, IsManager) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, idProject);
                    pstmt.setString(2, idUser);
                    pstmt.setString(3, idFamily);
                    pstmt.setBoolean(4, isManager);

                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error al insertar en la base de datos");
                }
            }
            System.out.println("Datos insertados en la tabla de colaboraciones");

            // Close the database connection
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar en la base de datos");
        }
    }
}
