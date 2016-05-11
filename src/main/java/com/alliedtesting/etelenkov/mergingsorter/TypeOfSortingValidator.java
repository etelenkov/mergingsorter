package com.alliedtesting.etelenkov.mergingsorter;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

// Validator for Type of sorting values
public class TypeOfSortingValidator implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        if (!(value.equals("Integer") || value.equals("String")))
            throw new ParameterException("Parameter " + name +
                    " should be [Integer] or [String] !");
    }
}
