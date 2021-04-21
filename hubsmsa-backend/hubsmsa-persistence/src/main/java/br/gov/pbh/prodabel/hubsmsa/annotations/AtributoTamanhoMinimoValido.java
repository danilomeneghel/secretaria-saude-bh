package br.gov.pbh.prodabel.hubsmsa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import br.gov.pbh.prodabel.hubsmsa.validator.AtributoTamanhoMinimoValidator;



@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtributoTamanhoMinimoValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface AtributoTamanhoMinimoValido {

	String message() default "É necessária a informação de pelo menos 3 caracteres para pesquisa pelo nome ou descrição do campo.";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    public abstract boolean ignoreCase() default false;
    
    long tamanhoMinimo() default 3;
	
}
