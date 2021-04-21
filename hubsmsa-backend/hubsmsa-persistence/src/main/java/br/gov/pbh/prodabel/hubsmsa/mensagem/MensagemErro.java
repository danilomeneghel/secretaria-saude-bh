package br.gov.pbh.prodabel.hubsmsa.mensagem;

/**
 * Mensagem do tipo erro.
 *
 * @author diogo.matos
 */
public class MensagemErro extends Mensagem {

    /**
     * Construtor recebendo o conteúdo da mensagem do tipo erro.
     * 
     * @param conteudo
     *            conteúdo da mensagem
     */
    public MensagemErro(String conteudo) {
        super("error", conteudo);
    }
    
}
