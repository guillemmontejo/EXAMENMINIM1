package dsa.models;

import javax.sound.sampled.Line;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Laboratorio {
    String idLaboratio;
    String nombreLaboratorio;
    Queue<Muestra> colaMuestras;

    public Laboratorio(String idLaboratio, String nombreLaboratorio) {
        this.idLaboratio = idLaboratio;
        this.nombreLaboratorio = nombreLaboratorio;
        this.colaMuestras = new LinkedList<>();

    }

    public String getIdLaboratio() {
        return idLaboratio;
    }

    public void setIdLaboratio(String idLaboratio) {
        this.idLaboratio = idLaboratio;
    }

    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public Queue<Muestra> getColaMuestras() {
        return colaMuestras;
    }

    public void setColaMuestras(Queue<Muestra> colaMuestras) {
        this.colaMuestras = colaMuestras;
    }

    public int addMuestra(Muestra m){
        try{
            this.colaMuestras.add(m);
        }
        catch (ExceptionInInitializerError e)
        {
            return 400;//400 Bad Request
        }
        catch (IndexOutOfBoundsException e){
            return 507;//Insufficient storage
        }
        return 201;//201 Created
    }

    public void processMuestra(){
        this.colaMuestras.poll();
    }
}
