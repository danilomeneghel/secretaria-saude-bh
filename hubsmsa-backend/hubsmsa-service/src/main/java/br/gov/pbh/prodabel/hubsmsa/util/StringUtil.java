package br.gov.pbh.prodabel.hubsmsa.util;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;

public class StringUtil {
	
	private static String STRING_VAZIO = "";
	
	public static String adicionarMask(String value, String mask) {
		
		MaskFormatter maskFormatter;
		try {
			maskFormatter = new MaskFormatter(mask);
			maskFormatter.setAllowsInvalid(true);
			maskFormatter.setValueContainsLiteralCharacters(false);
			if(STRING_VAZIO.equals(value)) {
				return STRING_VAZIO;
			}else {
				return maskFormatter.valueToString(value);
			}
		} catch (ParseException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}

	}

}
