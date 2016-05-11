package com.alliedtesting.etelenkov.myapp;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

class JCommanderParameters {

    // Help
    @Parameter(names = {"-h", "--help"},
            description = "Help",
            help = true)
    boolean pHelp;

    // Descending order combining
    @Parameter(names = {"-d", "--descending"},
            description = "Descending order combining")
    boolean pDescendingOrder = false;

    // Charset of files
    @Parameter(names = {"-c", "--charset"},
            description = "Charset of files",
            converter = CharsetConverter.class)
    Charset pCharset = Charset.defaultCharset();

    // Type of sorting values [ Integer | String ]
    @Parameter(names = {"-v", "--values"},
            description = "Type of sorting values [ Integer | String ]",
            validateWith = TypeOfSortingValidator.class)
    String pValuesType = "Integer";

    // Input (source) files (variable arity)
    @Parameter(names = {"-i", "--input"},
            description = "Input (source) files",
            //validateWith = FilesValidator.class,
            converter = FileConverter.class,
            variableArity = true)
    List<File> pInputFiles;

    // Output (resulting) file
    @Parameter(names = {"-o", "--output"},
            description = "Output (resulting) file",
            //validateWith = FilesValidator.class,
            converter = FileConverter.class)
    File pOutputFile;

    // Rewrite existing output (resulting) file
    @Parameter(names = {"-r", "--rewrite"},
            description = "Rewrite existing output (resulting) file")
    boolean pRewriteOutputFile = false;

    /*// Validator for Files (should exist and be not a directory
    private class FilesValidator implements IParameterValidator {
        public FilesValidator() {
        }

        public void validate(String name, String value) throws ParameterException {
            File f = new File(value);
            //if (!f.exists()) throw new ParameterException("File [" + value + "] does not exist!");
            if (f.isDirectory()) throw new ParameterException("File [" + value + "] is a directory!");
        }
    }*/

    // Converter for Charset parameter
    private class CharsetConverter implements IStringConverter<Charset> {
        public CharsetConverter() {
        }

        @Override
        public Charset convert(String s) {
            return Charset.forName(s);
        }
    }
}



