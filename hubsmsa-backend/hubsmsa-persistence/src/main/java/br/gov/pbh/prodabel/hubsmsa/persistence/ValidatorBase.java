package br.gov.pbh.prodabel.hubsmsa.persistence;


import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorBase {

    private ValidatorBase() {
        throw new IllegalStateException("Classe utilitária");
    }

   private static Validator getValidator() {
       return Validation.buildDefaultValidatorFactory().getValidator();
   }

   public static <T> void validate(T objeto) {
       Set<ConstraintViolation<T>> violations = getValidator().validate(objeto);
       if (!violations.isEmpty()) {
          throw new ConstraintViolationException(
                  new HashSet<ConstraintViolation<?>>(violations));
       }
   }
}
