package br.gov.pbh.prodabel.hubsmsa.validator;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;


public class EnumListValidator implements ConstraintValidator<EnumValues, List<String>> {

    private EnumValues annotation;

    @Override
    public void initialize(EnumValues annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(List<String> valuesForValidation, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;

        List<String> enumValues = recuperarConstantesEnum();

        if (!this.annotation.acceptNull() && valuesForValidation == null) {
            result = false;
        } else if (valuesForValidation != null) {
            for (String value : valuesForValidation) {
                if (enumValues.stream().noneMatch(enumValue -> enumValue.equals(value)
                        || (this.annotation.ignoreCase() && enumValue.equalsIgnoreCase(value)))) {
                    result = false;
                    break;
                }
            }

        }
        return result;
    }

    private List<String> recuperarConstantesEnum() {
        List<String> valuesString = new ArrayList<>();
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        for (Object object : enumValues) {
            valuesString.add(object.toString());
        }

        return valuesString;
    }

}
