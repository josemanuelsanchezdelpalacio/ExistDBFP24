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


import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.nio.file.Path;

public class InsertarProyectos {
    public static void insertar() {
        try {
            Connection con = ConexionMySQL.conectar("FP24MJO");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Path p = Path.of("target/proyectosFinal.xml");
            Document document = builder.parse(p.toFile());

            NodeList nodeList = document.getElementsByTagName("Proyecto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String titulo = element.getElementsByTagName("tituloProyecto").item(0).getTextContent();
                String fechaInicio = element.getElementsByTagName("fechaInicio").item(0).getTextContent();
                String fechaFin = element.getElementsByTagName("fechaFin").item(0).getTextContent();
                String estado = "Pendiente";
                if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                    estado = "Completado";
                } else if (!fechaInicio.isEmpty()) {
                    estado = "En Curso";
                }

                try {
                    String query = "INSERT IGNORE INTO PROJECT (Title, State, InitDate, EndDate) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, estado);
                    SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");

                    //formateo las fechas
                    if (!fechaInicio.isEmpty()) {
                        Date parsedDate = sdfInput.parse(fechaInicio);
                        pstmt.setString(3, sdfOutput.format(parsedDate));
                    } else {
                        pstmt.setNull(3, Types.DATE);
                    }

                    if (!fechaFin.isEmpty()) {
                        Date parsedDate = sdfInput.parse(fechaFin);
                        pstmt.setString(4, sdfOutput.format(parsedDate));
                    } else {
                        pstmt.setNull(4, Types.DATE);
                    }
                    pstmt.executeUpdate();

                    //obtengo el ID del proyecto recien insertado
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    int projectId = -1;
                    if (generatedKeys.next()) {
                        projectId = generatedKeys.getInt(1);
                    }

                    if (projectId != -1) {
                        //inserto colaboracion con campos de usuario y familia nulos
                        String insertCollaborationQuery = "INSERT INTO COLLABORATION (IdProject, IdUser, IdFamily, IsManager) VALUES (?, NULL, NULL, ?)";
                        PreparedStatement collaborationPstmt = con.prepareStatement(insertCollaborationQuery);
                        collaborationPstmt.setInt(1, projectId);
                        collaborationPstmt.setBoolean(2, true);
                        collaborationPstmt.executeUpdate();
                    } else {
                        System.out.println("No se encontró el ID del proyecto recién insertado");
                    }

                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                    System.out.println("Error en la operación de la base de datos");
                }
            }
            System.out.println("Datos de PROJECT subidos");
            con.close();
        } catch (ParserConfigurationException | IOException | SAXException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la operación de la base de datos");
        }
    }
}

