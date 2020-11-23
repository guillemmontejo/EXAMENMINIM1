package dsa.models;

public class Informe {
    String resultado;
    String comentario;

    public Informe(String resultado, String comentario) {
        this.resultado = resultado;
        this.comentario = comentario;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
