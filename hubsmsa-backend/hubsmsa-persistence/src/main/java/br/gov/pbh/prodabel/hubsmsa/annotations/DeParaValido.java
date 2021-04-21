package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.DeParaValidator;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeParaValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface DeParaValido {
	
	String message() default "Pelo menos um campo deve ser informado para pesquisa";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    public abstract boolean ignoreCase() default false;
}
