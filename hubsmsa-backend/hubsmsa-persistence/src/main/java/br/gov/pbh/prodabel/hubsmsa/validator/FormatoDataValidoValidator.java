package br.gov.pbh.prodabel.hubsmsa.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.FormatoDataValido;

public class FormatoDataValidoValidator implements ConstraintValidator<FormatoDataValido, String>  {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
  public void initialize(FormatoDataValido a) {
		
	}

	@Override
	public boolean isValid(String data, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;

		dateFormat.setLenient(false);
		
		if(data != null) {
			try {
				dateFormat.parse(data);
				isValid = true;
			} catch (ParseException e) {
				//data invalida, retorna false
			}
		}

		return isValid;
	}
	


}
