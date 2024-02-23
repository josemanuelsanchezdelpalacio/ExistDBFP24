import CSVtoXML.LeerCSV_Centros;
import CSVtoXML.LeerCSV_Proyectos;
import ConsultasXQuery.ConsultaCentros;
import ConsultasXQuery.ConsultaColaboraciones;
import ConsultasXQuery.ConsultaFamilias;
import ConsultasXQuery.ConsultaProyectos;
import DatosXMLaBD.InsertarCentros;
import DatosXMLaBD.InsertarColaboraciones;
import DatosXMLaBD.InsertarFamilias;
import DatosXMLaBD.InsertarProyectos;
import ManejoDB.CreacionTablas;
import XML_a_ExistDB.SubirArchivosExistDB;
import colaboraciones.XMLColaboraciones;
import libs.Leer;

public class Main {

    public static void main(String[] args) {
        boolean salir = false;
        int opcion;

        do {
            System.out.println("1. Crear tablas");
            System.out.println("2. Transformar de csv a xml");
            System.out.println("3. Subir archivos XML a la base de datos eXist");
            System.out.println("4. Exportar datos XQuery a XML");
            System.out.println("5. Insertar datos de XML a BD");
            System.out.println("0. Volver al menu principal");

            opcion = Leer.pedirEntero("Introduce una opcion: ");

            switch (opcion) {
                case 1 -> {
                    //creación de las tablas
                    CreacionTablas.crear();
                    System.out.println("Tablas creadas");
                }
                case 2 -> {
                    //leer proyectos desde CSV y guardar en XML
                    LeerCSV_Centros.leerCentros();
                    LeerCSV_Proyectos.leerProyectos();
                }
                case 3 -> {
                    //subir archivos XML a la base de datos eXist
                    SubirArchivosExistDB.subirArchivos();
                }
                case 4 -> {
                    //consultas eXist-DB y creación de archivos XML
                    ConsultaCentros.listarCentros();
                    ConsultaProyectos.listarProyectos();
                    ConsultaFamilias.listarFamilias();
                }
                case 5 -> {
                    //insertar los datos a las tablas
                    InsertarCentros.insertar();
                    InsertarFamilias.insertar();
                    InsertarProyectos.insertar();
                    XMLColaboraciones.procesarXML();

                }
                case 0 -> salir = true;
                default -> System.out.println("Opcion no valida");
            }
        } while (!salir);
    }
}

