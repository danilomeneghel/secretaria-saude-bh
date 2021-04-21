package br.gov.pbh.prodabel.hubsmsa.exception;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import br.gov.pbh.prodabel.hubsmsa.mensagem.Mensagem;

@Singleton
public class NegocioExceptionI18N {

    private static final Logger LOGGER = Logger.getLogger(NegocioExceptionI18N.class.getName());

    public void internacionalizar(Locale locale, NegocioException negocioException) {

        ResourceBundle bundle = null;

        try {

            bundle = ResourceBundle.getBundle("messages", locale, Thread.currentThread().getContextClassLoader());

        } catch (MissingResourceException e) {

            LOGGER.log(Level.WARNING, "Arquivo de mensagens não encontrado.");

            return;
        }

        List<Mensagem> messagens = negocioException.getMessages();

        for (Mensagem mensagem : messagens) {

            try {

                String valor = bundle.getString(mensagem.getMsg());

                mensagem.setMsg(valor);

            } catch (MissingResourceException e) {

                LOGGER.log(Level.WARNING, "Propriedade '" + mensagem.getMsg() + "' não encontrada.");
            }
        }
    }

}
