package CSVtoXML;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import objetosCSV.Centro;
import objetosCSV.Centros;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LeerCSV_Centros {

    static ArrayList<Centro> centrosLista = new ArrayList<>();

    public static void leerCentros() {
        try {
            List<String> lineas = Files.readAllLines(Path.of("src/main/resources/CentrosCFGMyS.csv"));
            lineas.remove(lineas.get(0));

            Centros centros = new Centros();
            for (String linea : lineas) {
                linea = linea.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
                String[] centro = linea.split(",");

                Centro centroAux = new Centro();

                centroAux.setDistancia(centro[0]);
                centroAux.setProvincia(centro[1]);
                centroAux.setLocalidad(centro[2]);
                centroAux.setCodigo(centro[3]);
                centroAux.setDenomCorta(centro[4]);
                centroAux.setNombre(centro[5]);
                centroAux.setDenominacion(centro[6]);
                centroAux.setDireccion(centro[7]);
                centroAux.setNaturaleza(centro[8]);
                centroAux.setTelefono(centro[9]);
                centroAux.setCorreoElectronico(centro[10]);
                centroAux.setCodigoPostal(centro[11]);

                centrosLista.add(centroAux);
            }
            centros.setCentros(centrosLista);
            try {
                JAXBContext contexto = JAXBContext.newInstance(Centros.class);
                Marshaller marshaller = contexto.createMarshaller();
                marshaller.setProperty(marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(centros, new FileWriter("target/centros.xml"));
                System.out.println("XML Centros creado");
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
