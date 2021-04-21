package br.gov.pbh.prodabel.hubsmsa.util;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_FORMATAR_CNPJ;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_FORMATAR_TELEFONE;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
public class TextFormatUtil {

	private static final Logger LOG = LoggerFactory.getLogger(TextFormatUtil.class);
    private static final String STRING_VAZIO = "";
	
	/**
	 * Formatar cnpj.
	 *
	 * @param cnpj the cnpj
	 * @return the string
	 */
	public static String formatarCnpj(String cnpj) {
		if(StringUtils.isEmpty(cnpj) || !StringUtils.isNumeric(cnpj))
			return cnpj;
	    try {
	        MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
	        mask.setValueContainsLiteralCharacters(false);
	        return mask.valueToString(cnpj);
	    } catch (ParseException ex) {
	    	LOG.error(ERRO_AO_FORMATAR_CNPJ, ex);
	    	return cnpj;
	    }
	}
	
	/**
	 * Formatar telefone.
	 *
	 * @param telefone the telefone
	 * @return the string
	 */
	public static String formatarTelefone(String telefone) {
	  
      MaskFormatter maskFormatter;
      try {
          String mask = (telefone.length() == 10) ? "(##) ####-####" : "(##) #####-####";
          maskFormatter = new MaskFormatter(mask);
          maskFormatter.setAllowsInvalid(true);
          maskFormatter.setValueContainsLiteralCharacters(false);
          if (!telefone.matches(".*[0-9].*")) {
            return STRING_VAZIO;
          }
          if(STRING_VAZIO.equals(telefone)) {
              return STRING_VAZIO;
          }else {
              return maskFormatter.valueToString(telefone.replaceAll("[^\\d.]", ""));
          }
      } catch (ParseException e) {
        LOG.error(ERRO_AO_FORMATAR_TELEFONE, e);
        return telefone;
      }

  }
	
  /**
   * Instantiates a new text format util.
   */
  private TextFormatUtil() {}

}
