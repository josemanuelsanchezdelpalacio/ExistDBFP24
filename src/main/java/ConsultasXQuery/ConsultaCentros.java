package ConsultasXQuery;

import conexiones.ConexionExistDB;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class ConsultaCentros {

    static Collection col = ConexionExistDB.conexionExistDb();

    public static void listarCentros() {
        final String driver = "org.exist.xmldb.DatabaseImpl";

        Class cl = null;
        try {
            //inicializo el driver
            cl = Class.forName(driver);

            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            Collection col = null;
            //obtengo la coleccion
            col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/");
            XPathQueryService xqs = (XPathQueryService) col.getService("XQueryService", "3.0");
            xqs.setProperty("indent", "yes");

            //consulta para obtener las entidades
            ResourceSet resultCentros = xqs.query("<entidades>{\n" +
                    "    let $x := doc('coleccionXML/centros.xml')//centros\n" +
                    "    for $centro in $x\n" +
                    "    where not(empty($centro/codigo)) and not(empty($centro/nombre)) and not(empty($centro/correoElectronico)) and not(empty($centro/codigoPostal))\n" +
                    "    return\n" +
                    "    <entidad>\n" +
                    "        <codigo>{distinct-values($centro/codigo/text())}</codigo>\n" +
                    "        <nombre>{$centro/nombre/text()}</nombre>\n" +
                    "        <correoElectronico>{$centro/correoElectronico/text()}</correoElectronico>\n" +
                    "        <web>{$centro/codigoPostal/text()}</web>\n" +
                    "    </entidad>\n" +
                    "}\n" +
                    "</entidades>\n");

            //obtengo los resultados
            ResourceIterator i = resultCentros.getIterator();
            Resource res = null;

            //creo el archivo xml y escribo los resultados en el
            FileWriter fw = new FileWriter("target/centrosFinal.xml");
            while (i.hasMoreResources()) {
                res = i.nextResource();
                fw.write(res.getContent().toString());
            }
            fw.close();

            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Source source = new StreamSource(new StringReader(res.getContent().toString()));
                Result result = new StreamResult(new File("target/centrosFinal.xml"));

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
            //cierro la conexion
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