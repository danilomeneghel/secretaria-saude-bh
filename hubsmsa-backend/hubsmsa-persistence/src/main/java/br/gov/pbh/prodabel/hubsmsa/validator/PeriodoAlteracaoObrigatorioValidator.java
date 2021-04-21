package br.gov.pbh.prodabel.hubsmsa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoObrigatorio;

public class PeriodoAlteracaoObrigatorioValidator implements ConstraintValidator<PeriodoAlteracaoObrigatorio, Object>  {
	
	private String dataInicial;
	private String dataFinal;
	
	@Override
  public void initialize(PeriodoAlteracaoObrigatorio a) {
		this.dataInicial = a.dataInicial();
		this.dataFinal = a.dataFinal();
	}

	@Override
	public boolean isValid(Object t, ConstraintValidatorContext cvc) {

		boolean isValid = Boolean.FALSE;

		final String dataInicialValor;
		final String dataFinalValor;
		
		try {
			dataInicialValor = BeanUtils.getProperty(t, this.dataInicial);
			dataFinalValor = BeanUtils.getProperty(t, this.dataFinal);

			isValid = StringUtils.isNotBlank(dataInicialValor) && StringUtils.isNotBlank(dataFinalValor);

		} catch (Exception e) {
			// Continua retornando false
		}

		return isValid;
	}
	


}
