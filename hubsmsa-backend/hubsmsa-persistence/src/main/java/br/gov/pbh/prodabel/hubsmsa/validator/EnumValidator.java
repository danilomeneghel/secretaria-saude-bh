package br.gov.pbh.prodabel.hubsmsa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.EnumValues;


public class EnumValidator implements ConstraintValidator<EnumValues, String> {
	
	private EnumValues annotation;

	@Override
	public void initialize(EnumValues annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
		boolean result = false;

		Object[] enumValues = this.annotation.enumClass().getEnumConstants();

		if (enumValues != null) {
			for (Object enumValue : enumValues) {
				if (this.annotation.acceptNull() && valueForValidation == null
						|| enumValue.toString().equals(valueForValidation)
						|| (this.annotation.ignoreCase() && enumValue.toString().equalsIgnoreCase(valueForValidation))) {
					result = true;
					break;
				}
			}
		}

		return result;
	}
}
