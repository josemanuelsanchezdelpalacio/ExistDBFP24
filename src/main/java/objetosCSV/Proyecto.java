package objetosCSV;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"coordinadorCentro", "tituloProyecto", "autorizacion", "continuidad", "coordinacion", "contacto", "centrosAnexion"})
public class Proyecto {

    private String coordinadorCentro, tituloProyecto, autorizacion, continuidad, coordinacion, contacto, centrosAnexion;

    public Proyecto() {
    }

    @XmlElement
    public String getCoordinadorCentro() {
        return coordinadorCentro;
    }

    public void setCoordinadorCentro(String coordinadorCentro) {
        this.coordinadorCentro = coordinadorCentro;
    }

    @XmlElement
    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    @XmlElement
    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    @XmlElement
    public String getContinuidad() {
        return continuidad;
    }

    public void setContinuidad(String continuidad) {
        this.continuidad = continuidad;
    }

    @XmlElement
    public String getCoordinacion() {
        return coordinacion;
    }

    public void setCoordinacion(String coordinacion) {
        this.coordinacion = coordinacion;
    }

    @XmlElement
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @XmlElement
    public String getCentrosAnexion() {
        return centrosAnexion;
    }

    public void setCentrosAnexion(String centrosAnexion) {
        this.centrosAnexion = centrosAnexion;
    }
}