package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.FormatoHoraValidator;

@Target({ElementType.METHOD ,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FormatoHoraValidator.class)
public @interface FormatoHoraValido {
	String message() default "A hora informada é inválida.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
}
