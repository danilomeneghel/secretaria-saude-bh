package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.DateTimeValidator;
import br.gov.pbh.prodabel.hubsmsa.validator.PeriodoAlteracaoValidoValidator;

@Target({ElementType.METHOD ,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PeriodoAlteracaoValidoValidator.class, DateTimeValidator.class})
public @interface PeriodoAlteracaoValido {

	String message() default "0(s) campo(s) Período de Alteração devem ter data final anterior a data inicial.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
    
    String dataInicial();
    
    String dataFinal();
   
    boolean periodoObrigatorio() default true;
    
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
    	PeriodoAlteracaoValido[] value();
    }
}
