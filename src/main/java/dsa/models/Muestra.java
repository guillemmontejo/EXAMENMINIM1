package dsa.models;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Muestra {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String idMuestra;
    String idClinico;
    String idPersona;
    Date fechaExtraccion;
    String idLab;
    Informe informe;

    public Muestra(String idMuestra, String idClinico, String idPersona, Date fechaExtraccion, String idLab) {
        this.idMuestra = idMuestra;
        this.idClinico = idClinico;
        this.idPersona = idPersona;
        this.fechaExtraccion = fechaExtraccion;
        this.idLab = idLab;
    }

    public Muestra(){

    }

    public Muestra(String idMuestra, String idClinico, String idPersona, Date fechaExtraccion, String idLab, Informe informe) {
        this.idMuestra = idMuestra;
        this.idClinico = idClinico;
        this.idPersona = idPersona;
        this.fechaExtraccion = fechaExtraccion;
        this.idLab = idLab;
        this.informe = informe;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    public String getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(String idMuestra) {
        this.idMuestra = idMuestra;
    }

    public String getIdClinico() {
        return idClinico;
    }

    public void setIdClinico(String idClinico) {
        this.idClinico = idClinico;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public Date getFechaExtraccion() {
        return fechaExtraccion;
    }

    public void setFechaExtraccion(Date fechaExtraccion) {
        this.fechaExtraccion = fechaExtraccion;
    }

    public String getIdLab() {
        return idLab;
    }

    public void setIdLab(String idLab) {
        this.idLab = idLab;
    }
    public String toString(){
        return "ID muestra: " + this.getIdMuestra() + " |ID Persona: " + this.getIdPersona() + " |ID Clínico: " + this.getIdClinico() + " |ID Laboratorio: " + this.getIdLab() + " |Fecha:" + this.getFechaExtraccion().toString();
    }
}
