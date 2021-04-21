package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.CpfCnpjValidator;

/**
 * Anotação responsável por validar valores de cpf e cnpj
 *
 * @author alysson.cordeiro@ctis.com.br
 */
@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {
    String message() default "CPF/CNPJ inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
