package colaboraciones;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Date;

@XmlRootElement(name = "Proyecto")
@XmlType(propOrder = {"centroCoordinador", "tituloProyecto", "fechaInicio", "fechaFin", "coordinacion", "contacto", "centroanexionado"})
public class Proyecto {

    private String centroCoordinador, tituloProyecto, coordinacion, contacto, centroAnexionado;
    private Date fechaInicio, fechaFin;

    @XmlElement(name = "centroCoordinador")
    public String getCentroCoordinador() {
        return centroCoordinador;
    }

    public void setCentroCoordinador(String centroCoordinador) {
        this.centroCoordinador = centroCoordinador;
    }

    @XmlElement(name = "tituloProyecto")
    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    @XmlElement(name = "fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @XmlElement(name = "fechaFin")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @XmlElement(name = "coordinacion")
    public String getCoordinacion() {
        return coordinacion;
    }

    public void setCoordinacion(String coordinacion) {
        this.coordinacion = coordinacion;
    }

    @XmlElement(name = "contacto")
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @XmlElement(name = "centroanexionado")
    public String getCentroAnexionado() {
        return centroAnexionado;
    }

    public void setCentroAnexionado(String centroAnexionado) {
        this.centroAnexionado = centroAnexionado;
    }
}
