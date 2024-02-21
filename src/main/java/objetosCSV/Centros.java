package objetosCSV;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name="centros")
public class Centros {
    public ArrayList<Centro> centros = new ArrayList<>();

    public Centros() {}

    public void setCentros(ArrayList<Centro> centros) {
        this.centros = centros;
    }
}
