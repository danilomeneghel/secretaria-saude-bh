package br.gov.pbh.prodabel.hubsmsa.validator;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.HoraPeriodo;

public class HoraPeriodoValidator implements ConstraintValidator<HoraPeriodo, Object> {
	private String horaInicio;
	private String horaFim;
	private static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Override
	public void initialize(HoraPeriodo a) {
		this.horaInicio = a.horaInicio();
		this.horaFim = a.horaFim();
	}

	@Override
	public boolean isValid(Object t, ConstraintValidatorContext cvc) {

		boolean isValid = true;

		final String horaInicioValor;
		final String horaFimValor;

		try {

			horaInicioValor = BeanUtils.getProperty(t, this.horaInicio);
			horaFimValor = BeanUtils.getProperty(t, this.horaFim);
			
			if(horaInicioValor == null && horaFimValor == null) {
				isValid = true;
			}
			else if (horaInicioValor != null && horaFimValor != null) {

				isValid = LocalTime.parse(horaInicioValor, formatterTime)
						.isBefore(LocalTime.parse(horaFimValor, formatterTime)) || LocalTime.parse(horaInicioValor,formatterTime).equals(LocalTime.parse(horaFimValor, formatterTime));;
			}

		} catch (Exception e) {
			// Continua retornando true
		}

		return isValid;
	}
	

}
