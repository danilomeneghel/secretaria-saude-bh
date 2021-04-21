package br.gov.pbh.prodabel.hubsmsa.validator;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.FormatoHoraValido;

public class FormatoHoraValidator implements ConstraintValidator<FormatoHoraValido, String> {

	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
			.withResolverStyle(ResolverStyle.STRICT);

	@Override
  public void initialize(FormatoHoraValido a) {

	}

	@Override
	public boolean isValid(String data, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;

		if (data != null) {
			try {
				LocalTime.parse(data, dateFormat);
				isValid = true;
			} catch (DateTimeException e) {
				// data invalida, retorna false
			}
		}

		return isValid;

	}
}
