package CSVtoXML;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import objetosCSV.Proyecto;
import objetosCSV.Proyectos;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeerCSV_Proyectos {

    static ArrayList<Proyecto> proyectosLista = new ArrayList<>();

    public static void leerProyectos() {
        try {
            List<String> lineas = Files.readAllLines(Path.of("src/main/resources/proyectos.csv"));
            lineas.remove(lineas.get(0));

            Proyectos proyectos = new Proyectos();
            for (String linea : lineas) {
                linea = linea.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
                String[] proyecto = linea.split(",");

                Proyecto proyectoAux = new Proyecto();

                //por si hay campos vacios lo sustituyo con una cadena vacia
                proyectoAux.setCoordinadorCentro(proyecto.length > 0 ? proyecto[0] : "");
                proyectoAux.setTituloProyecto(proyecto.length > 1 ? proyecto[1] : "");
                proyectoAux.setAutorizacion(proyecto.length > 2 ? proyecto[2] : "");
                proyectoAux.setContinuidad(proyecto.length > 3 ? proyecto[3] : "");
                proyectoAux.setCoordinacion(proyecto.length > 4 ? proyecto[4] : "");
                proyectoAux.setContacto(proyecto.length > 5 ? proyecto[5] : "");
                proyectoAux.setCentrosAnexion(proyecto.length > 6 ? proyecto[6] : "");

                proyectosLista.add(proyectoAux);

            }

            proyectos.setProyectos(proyectosLista);
            try {
                JAXBContext contexto = JAXBContext.newInstance(Proyectos.class);
                Marshaller marshaller = contexto.createMarshaller();
                marshaller.setProperty(marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(proyectos, new FileWriter("target/proyectos.xml"));
                System.out.println("XML Proyectos creado");
            } catch (JAXBException ex) {
                System.out.println("La clase seleccionada no permite usar JAXB");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error de lectura o a la hora de transformar");
        }
    }
}
