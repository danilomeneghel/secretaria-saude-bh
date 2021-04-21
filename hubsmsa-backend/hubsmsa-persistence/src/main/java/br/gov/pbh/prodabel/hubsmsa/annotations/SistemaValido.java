package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.SistemaValidator;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SistemaValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface SistemaValido {
	
	String message() default "Pelo menos um campo deve ser informado para pesquisa";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    public abstract boolean ignoreCase() default false;

}
