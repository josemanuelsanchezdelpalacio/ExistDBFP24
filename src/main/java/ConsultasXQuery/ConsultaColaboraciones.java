package ConsultasXQuery;

import conexiones.ConexionExistDB;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class ConsultaColaboraciones {

    static Collection col = ConexionExistDB.conexionExistDb();

    public static void listarColaboraciones() {
        final String driver = "org.exist.xmldb.DatabaseImpl";

        // Inicializar el driver
        Class cl = null;
        try {
            cl = Class.forName(driver);

            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            Collection col = null;

            col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/");
            XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
            xqs.setProperty("indent", "yes");

            // Consulta para obtener los proyectos
            ResourceSet resultProyectos = xqs.query("");

            // Obtener los resultados
            ResourceIterator i = resultProyectos.getIterator();
            Resource res = null;

            // Crear el FileWriter fuera del bucle
            FileWriter fw = new FileWriter("target/colaboracionesFinal.xml");

            // Escribir los resultados en el archivo XML
            while (i.hasMoreResources()) {
                res = i.nextResource();
                fw.write(res.getContent().toString());
            }
            fw.close();

            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Source source = new StreamSource(new StringReader(res.getContent().toString()));
                Result result = new StreamResult(new File("target/colaboracionesFinal.xml"));

                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                transformer.transform(source, result);
            } catch (TransformerException e) {
                throw new RuntimeException("Error durante la transformación XML", e);
            }

            System.out.println("Consulta realizada correctamente");

        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo encontrar la clase");
        } catch (XMLDBException e) {
            System.out.println("Error con la colección");
        } catch (IOException e) {
            System.out.println("Error durante la lectura del XML");
        } catch (InstantiationException e) {
            System.out.println("No se puede instanciar la bd");
        } catch (IllegalAccessException e) {
            System.out.println("Error al acceder a la colección");
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    System.out.println("Error al cerrar la conexión");
                }
            }
        }
    }
}