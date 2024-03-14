package DatosXMLaBD;

import conexiones.ConexionMySQL;
import entities.ProjectEntity;
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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InsertarProyectos {
    public static void insertar() {
        try {
            //creo la conexión a la base de datos MySQL
            Connection con = ConexionMySQL.conectar("FP24MJO");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/proyectosFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("Proyecto");
            //creo los objetos de la entidad Proyecto
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String titulo = element.getElementsByTagName("tituloProyecto").item(0).getTextContent();
                String fechaInicio = element.getElementsByTagName("fechaInicio").item(0).getTextContent();
                String fechaFin = element.getElementsByTagName("fechaFin").item(0).getTextContent();
                String estado = "Pendiente";

                //compruebo el estado segun las fechas disponibles
                if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                    estado = "Completado";
                } else if (!fechaInicio.isEmpty()) {
                    estado = "En Curso";
                }

                //inserto los datos
                try {
                    String query = "INSERT IGNORE INTO PROJECT (Title, State, InitDate, EndDate) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, estado);

                    //modifico las fechas para formatearlas
                    if (!fechaInicio.isEmpty()) {
                        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = sdfInput.parse(fechaInicio);
                        pstmt.setString(3, sdfOutput.format(parsedDate));
                    } else {
                        pstmt.setNull(3, Types.DATE);
                    }
                    if (!fechaFin.isEmpty()) {
                        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = sdfInput.parse(fechaFin);
                        pstmt.setString(4, sdfOutput.format(parsedDate));
                    } else {
                        pstmt.setNull(4, Types.DATE);
                    }
                    pstmt.executeUpdate();

                    //obtengo el ID del proyecto recién insertado
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    int projectId = -1;
                    if (generatedKeys.next()) {
                        projectId = generatedKeys.getInt(1);
                    }

                    //insercion de una colaboración asociada al proyecto
                    if (projectId != -1) {
                        String insertCollaborationQuery = "INSERT INTO COLLABORATION (IdProject, IdUser, IdFamily, IsManager) VALUES (?, ?, ?, ?)";
                        PreparedStatement collaborationPstmt = con.prepareStatement(insertCollaborationQuery);
                        collaborationPstmt.setInt(1, projectId);
                        collaborationPstmt.setInt(2, -1);
                        collaborationPstmt.setInt(3, -1);
                        collaborationPstmt.setBoolean(4, true);
                        collaborationPstmt.executeUpdate();
                    }
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
