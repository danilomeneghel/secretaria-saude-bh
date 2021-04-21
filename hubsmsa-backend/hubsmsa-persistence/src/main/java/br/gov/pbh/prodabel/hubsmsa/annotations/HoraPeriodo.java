package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.HoraPeriodoValidator;

@Target({ElementType.METHOD ,ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HoraPeriodoValidator.class)
public @interface HoraPeriodo {
	
	String message() default "A hora final deve ser maior que a hora inicial.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    String horaInicio();
 
    String horaFim();
 
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
    	HoraPeriodo[] value();
    }
}
