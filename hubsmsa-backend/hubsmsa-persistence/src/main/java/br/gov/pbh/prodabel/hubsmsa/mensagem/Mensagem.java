package br.gov.pbh.prodabel.hubsmsa.mensagem;

public abstract class Mensagem {

    private String type;
    private String msg;

    public Mensagem() {
    }

    public Mensagem(String tipo, String msg) {
        this.type = tipo;
        this.msg = msg;
    }

    /**
     * Retorna o tipo da mensagem.
     * 
     * @return tipo da mensagem
     */
    public String getType() {
        return type;
    }

    /**
     * Retorna o conteúdo da mensagem.
     * 
     * @return conteúdo da mensagem
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg - Mensagem
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}