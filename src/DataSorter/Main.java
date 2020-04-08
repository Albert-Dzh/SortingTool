package DataSorter;

/**
 *
 * Program sorts input (from console/file)
 * and output result to console, or store in file.
 *
 * By default program will use params below:
 * #1: dataType: word
 * #2: sortingType: natural
 * #3: inputFile: none
 * #4: outputFile: none
 *
 * Or one can run it with args:
 * -dataType ("integer", "line", "long", "word")
 * -sortingType ("byCount", "natural")
 * -inputFile ("fileName" with data, that needs to be sorted)
 * -outputFile ("fileName", where sorted data will be stored)
 *
 */


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Main {

    private final static List<String> SORTTEMPLATE = Arrays.asList("byCount", "natural");
    private final static List<String> DATATEMPLATE = Arrays.asList("integer", "line", "long", "word");

    public static void main(String[] args) throws FileNotFoundException {

        String[] params = getParams(args);

        DataSorter dSorter = new DataSorter(params); // 0 - dataT; 1 - sortT; 2 - inF; 3 - outF

        dSorter.collect();
        dSorter.getResult();

    }

    static String[] getParams(String[] args) {
        String[] res = {"word", "natural", "nofile", "nofile"}; // default

        for (int i = 0; i < args.length; i++) {
            switch (args[i].toLowerCase()) { // suggest, it's a command?
                case "-sortingtype":
                    if (i + 1 >= args.length || isCommand(args[i + 1])) {
                        System.out.println("No sorting type defined!");
                        System.exit(0);
                    } else {
                        if (SORTTEMPLATE.contains(args[++i])) {
                            res[1] = args[i];
                        }
                    }
                    break;
                case "-datatype":
                    if (i + 1 >= args.length || isCommand(args[i + 1])) {
                        System.out.println("No data type defined!");
                        System.exit(0);
                    } else {
                        if (DATATEMPLATE.contains(args[++i])) {
                            res[0] = args[i];
                        }
                    }
                    break;
                case "-inputfile":
                    if (i + 1 >= args.length || isCommand(args[i + 1])) {
                        System.out.println("No input file defined!");
                        System.exit(0);
                    } else {
                        res[2] = args[++i];
                    }
                    break;
                case "-outputfile":
                    if (i + 1 >= args.length || isCommand(args[i + 1])) {
                        System.out.println("No output file defined!");
                        System.exit(0);
                    } else {
                        res[3] = args[++i];
                    }
                    break;
                default:
                    System.out.println("\"" + args[i] +
                            "\" isn't a valid parameter. It's skipped.");
            }
        }
        return res;
    }

    static boolean isCommand(String command) {
        return command.charAt(0) == '-';
    }
}
