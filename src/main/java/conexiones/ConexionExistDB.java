package conexiones;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;

public class ConexionExistDB {

    private static String driver = "org.exist.xmldb.DatabaseImpl";
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static String usuario = "admin";
    private static String password = "";

    static Collection col = null;

    public static Collection conexionExistDb() {
        try {
            //carga del driver
            Class cl = Class.forName(driver);
            //instancia de la base de datos
            Database database = (Database) cl.newInstance();
            //registro del driver
            DatabaseManager.registerDatabase(database);

            //creo la coleccion
            Collection col = DatabaseManager.getCollection(URI + "/db", usuario, password);
            CollectionManagementService service = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
            col = service.createCollection("coleccionXML");
            return col;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
        } catch (InstantiationException e) {
            System.out.println("Error de instancia de BD");
        } catch (IllegalAccessException e) {
            System.out.println("Error al acceder a la base de datos");
        } catch (XMLDBException e) {
            System.out.println("Error de XMLDB en newInstance");
        }
        return null;
    }
}
