package br.gov.pbh.prodabel.hubsmsa.enumeration;

import java.io.Serializable;

public class SortItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String campo;
    private String tipo;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
