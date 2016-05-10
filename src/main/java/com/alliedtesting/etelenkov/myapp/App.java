package com.alliedtesting.etelenkov.myapp;

import com.beust.jcommander.JCommander;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
//        // Logging system simple test example
//        Logger logger = LoggerFactory.getLogger(App.class);
//        logger.info("Hello World");
//        System.out.println("Hello World!");

        String[] argv = {"-i", "A.txt", "B.txt", "C.txt", "-r ", "-o", "Z.txt"};
        JCommanderParameters jcp = new JCommanderParameters();
        JCommander jc = new JCommander(jcp, argv);

        // If asked for help - Show help and terminate
        if (jcp.pHelp) {
            jc.setProgramName("myapp name"); // set program name
            jc.usage(); // print help

            // Print available charsets
            System.out.println("Available charsets:");
            for (Charset c : Charset.availableCharsets().values()) {
                System.out.print(c.toString() + " ");
            }

            return; // (!) terminate
        }

        // If output file is a directory
        if (jcp.pOutputFile.isDirectory()) {
            System.out.println("Output file [" + jcp.pOutputFile + "] is a directory!");
            return; // (!) terminate
        }
        // If output file exists
        if (jcp.pOutputFile.exists()) {
            if (jcp.pRewriteOutputFile) {
                if (!jcp.pOutputFile.delete()) {
                    System.out.println("Could not delete old output file [" + jcp.pOutputFile + "]!");
                    return; // (!) terminate
                }
            } else {
                System.out.println("Old output file exists [" + jcp.pOutputFile + "]!");
                return; // (!) terminate
            }
        }

        // Create new output file
        try {
            jcp.pOutputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create new output file [" + jcp.pOutputFile + "]!");
            return; // (!) terminate
        }

        // Create output file writer
        PrintWriter outputFileWriter;
        try {
            outputFileWriter = new PrintWriter(jcp.pOutputFile, jcp.pCharset.name());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Could not create output file writer for [" + jcp.pOutputFile + "]!");
            return; // (!) terminate
        }

        // Create Scanners list of input files (Collection of iterators for combining method)
        List<Iterator<String>> inputFileScannersList = new ArrayList<>(); // list of input files scanners
        try {
            for (File f : jcp.pInputFiles)
                inputFileScannersList.add(
                        new Scanner(f, jcp.pCharset.name()).useDelimiter(Pattern.compile("[\\r\\n]+")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not create input files Scanner list!");
            closeScannersList(inputFileScannersList); // (!) close opened Scanners
            return; // (!) terminate
        }

        // Create comparator for sorting combiner
        Comparator<String> comparator = (a, b) -> 0; // default value
        if (jcp.pValuesType.equals("Integer")) comparator = (a, b) -> Integer.parseInt(a) - Integer.parseInt(b);
        if (jcp.pValuesType.equals("String")) comparator = String::compareTo;
        // Set reversed ordering if asked
        if (jcp.pDescendingOrder) comparator = comparator.reversed();

        // Run sorting combiner
        Iterator<String> result = Iterators.sortingCombiner(inputFileScannersList, comparator);
        //System.out.println(Iterators.toList(result));

        // Write to file
        while (result.hasNext()) outputFileWriter.println(result.next());

        // Close Scanners of input files
        closeScannersList(inputFileScannersList);

        // Close output file writer
        outputFileWriter.close();
    }

    /**
     * Closes the List of Scanners even if some of the Scanner's closure process
     * generates an Exception. If an Exception on Scanner closure generated,
     * it's stack trace being printed, but the closure process does not
     * terminates for other Scanners in the List.
     *
     * @param list List of Scanners
     */
    private static void closeScannersList(List<? super Scanner> list) {
        for (Object o : list) {
            try {
                ((Scanner) o).close();
                //System.out.println("Closing Scanner [" + o + "]");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not close Scanner [" + o + "]");
            }
        }
    }
}