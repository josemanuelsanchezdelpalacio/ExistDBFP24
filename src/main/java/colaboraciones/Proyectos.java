package colaboraciones;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name="proyectos")
public class Proyectos {
    public ArrayList<Proyecto> proyectos = new ArrayList<>();

    public Proyectos() {}

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}