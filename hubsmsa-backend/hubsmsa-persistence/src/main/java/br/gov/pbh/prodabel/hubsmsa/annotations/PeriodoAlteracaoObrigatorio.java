package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.PeriodoAlteracaoObrigatorioValidator;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodoAlteracaoObrigatorioValidator.class)
public @interface PeriodoAlteracaoObrigatorio {

	String message() default "0(s) campo(s) Período de Alteração são de preenchimento obrigatório.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
    
    String dataInicial();
    
    String dataFinal();
    
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
    	PeriodoAlteracaoObrigatorio[] value();
    }
}
