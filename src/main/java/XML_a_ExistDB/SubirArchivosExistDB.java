package XML_a_ExistDB;

import conexiones.ConexionExistDB;
import libs.Leer;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import java.io.File;

public class SubirArchivosExistDB {

    public static void subirArchivos() {
        Collection col = null;
        try {
            col = ConexionExistDB.conexionExistDb();

            //pido los archivos
            File file = new File("src/main/resources/familias.xml");
            File file2 = new File("src/main/resources/proyectosFP.xml");
            File centrosFile = new File("target/centros.xml");
            File proyectosFile = new File("target/proyectos.xml");

            if (!file.canRead() || !file2.canRead() || !centrosFile.canRead() || !proyectosFile.canRead()) {
                System.out.println("Error al leer el documento XML.");
            } else {
                //compruebo si es un archivo
                Resource resource = col.createResource(file.getName(), "XMLResource");
                Resource resource2 = col.createResource(file2.getName(), "XMLResource");
                Resource resource3 = col.createResource(centrosFile.getName(), "XMLResource");
                Resource resource4 = col.createResource(proyectosFile.getName(), "XMLResource");

                //asigno el recurso a un archivo XML
                resource.setContent(file);
                resource2.setContent(file2);
                resource3.setContent(centrosFile);
                resource4.setContent(proyectosFile);

                System.out.println("Subido los archivos XML a la base de datos eXist");

                //guardo el recurso
                col.storeResource(resource);
                col.storeResource(resource2);
                col.storeResource(resource3);
                col.storeResource(resource4);
                System.out.println("Guardado el recurso");
            }

        } catch (XMLDBException e) {
            throw new RuntimeException("Error al subir archivos a eXist-DB", e);
        } finally {
            // Cerrar la conexión aquí en el bloque finally
            if (col != null) {
                try {
                    col.close();
                    System.out.println("Cerrada la conexion");
                } catch (XMLDBException e) {
                    System.out.println("Error al cerrar la conexión");
                }
            }
        }
    }
}