package br.gov.pbh.prodabel.hubsmsa.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.PeriodoAlteracaoValido;
import br.gov.pbh.prodabel.hubsmsa.dto.log.aplicacao.FiltroPesquisaLogAplicacaoDTO;

public class DateTimeValidator implements ConstraintValidator<PeriodoAlteracaoValido, FiltroPesquisaLogAplicacaoDTO>  {
	
	private String dataInicial;
	private String dataFinal;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
  public void initialize(PeriodoAlteracaoValido a) {
		this.dataInicial = a.dataInicial();
		this.dataFinal = a.dataFinal();
	}

	@Override
	public boolean isValid(FiltroPesquisaLogAplicacaoDTO value, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;

		final String dataInicialValor;
		final String dataFinalValor;
		
		try {
			dataInicialValor = BeanUtils.getProperty(value, this.dataInicial);
			dataFinalValor = BeanUtils.getProperty(value, this.dataFinal);

			if(dataInicialValor != null && dataFinalValor != null) {
				Date dataInicialValue = dateFormat.parse(dataInicialValor);
				Date dataFinalValue = dateFormat.parse(dataFinalValor);
				isValid = dataFinalValue.after(dataInicialValue) || dataFinalValue.equals(dataInicialValue);
			}
		} catch (Exception e) {
			// Continua retornando false
		}

		return isValid;
	}


	


}
