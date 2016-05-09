package com.alliedtesting.etelenkov.myapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {

    public static void main(String[] args) {
//        // Logging system simple test example
//        Logger logger = LoggerFactory.getLogger(App.class);
//        logger.info("Hello World");
//        System.out.println("Hello World!");

        try {
            Scanner fileAScanner = new Scanner(new File("A.txt")).useDelimiter(Pattern.compile("[\\r\\n]+"));
            Scanner fileBScanner = new Scanner(new File("B.txt")).useDelimiter(Pattern.compile("[\\r\\n]+"));

            Iterator<String> it = Iterators.sortingCombiner(
                    Arrays.asList(fileAScanner, fileBScanner),
                    (a, b) -> Integer.parseInt(a) - Integer.parseInt(b));

            System.out.println(Iterators.toList(it));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
