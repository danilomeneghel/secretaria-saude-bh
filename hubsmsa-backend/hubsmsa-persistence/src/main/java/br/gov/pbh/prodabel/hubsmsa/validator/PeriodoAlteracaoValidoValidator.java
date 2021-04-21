package br.gov.pbh.prodabel.hubsmsa.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoValido;

public class PeriodoAlteracaoValidoValidator implements ConstraintValidator<PeriodoAlteracaoValido, Object>  {
	
	private String dataInicial;
	private String dataFinal;
	private boolean periodoObrigatorio;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void initialize(PeriodoAlteracaoValido a) {
		this.dataInicial = a.dataInicial();
		this.dataFinal = a.dataFinal();
		this.periodoObrigatorio = a.periodoObrigatorio();
	}

	@Override
	public boolean isValid(Object t, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;

		final String dataInicialValor;
		final String dataFinalValor;
		
		try {
			dataInicialValor = BeanUtils.getProperty(t, this.dataInicial);
			dataFinalValor = BeanUtils.getProperty(t, this.dataFinal);

			if(dataInicialValor != null && dataFinalValor != null) {
				Date dataInicialValue = dateFormat.parse(dataInicialValor);
				Date dataFinalValue = dateFormat.parse(dataFinalValor);
				isValid = dataFinalValue.after(dataInicialValue) || dataFinalValue.equals(dataInicialValue);
			}
			else if(!this.periodoObrigatorio) {
				isValid = Boolean.TRUE;
			}
		} catch (Exception e) {
			// Continua retornando false
		}

		return isValid;
	}
	


}
