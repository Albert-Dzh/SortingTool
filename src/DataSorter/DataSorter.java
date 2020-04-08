package DataSorter;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DataSorter {

    private final Scanner in;
    private final String DATATYPE;
    private final String SORTTYPE;
    private final File OUTFILE;
    private int counter;
    private Map<Object, Integer> sortedData = new TreeMap<>();


    public DataSorter(String[] params) throws FileNotFoundException {
        DATATYPE = params[0].toLowerCase();
        SORTTYPE = params[1].toLowerCase();
        in = params[2].equals("nofile") ?
                new Scanner(System.in) : new Scanner(new File(params[2]));
        OUTFILE = params[3].equals("nofile") ? null : new File(params[3]);
    }

    public void collect() {
        switch (DATATYPE) {
            case "line":
                collectLines();
            case "long":
            case "integer":
                collectNums();
                break;
            case "word":
                collectWords();
                break;
        }
    }

    private void collectLines() {

        while (in.hasNextLine()) {
            String line = in.nextLine();
            counter++;
            sortedData.put(line, sortedData.getOrDefault(line, 0) + 1);
        }
    }

    private void collectNums() {

        while (in.hasNext()) {

            String num = in.next();
            if (num.matches("[-+]?\\d+")) {
                long n = Long.parseLong(num);
                counter++;
                sortedData.put(n, sortedData.getOrDefault(n, 0) + 1);
            }
            else {
                System.out.printf("\"%s\" isn't a %s. It's skipped%n", num, DATATYPE);
            }
        }
    }

    private void collectWords() {

        while (in.hasNext()) {
            String word = in.next();
            counter++;
            sortedData.put(word, sortedData.getOrDefault(word, 0) + 1);
        }
    }

    public void getResult() {
        in.close();
        StringBuilder result = new StringBuilder();
        result.append("Total ").append(DATATYPE).append("s: ").append(counter).append(".\n");

        if (SORTTYPE.equals("natural")) {
            if (!DATATYPE.equals("line")) {
                sortedData.forEach((key, value) -> {
                    while (value-- != 0) {
                        result.append(key).append(" ");
                    }
                });
            }
            else {
                sortedData.forEach((key, value) -> {
                    while (value-- != 0) {
                        result.append(key).append("\n");
                    }
                });
            }
        }
        else {
            int finalCounter = counter;
            sortedData.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                    forEach(o -> {
                        String percents = String.format("%.1f%%%n", (double)o.getValue() / (double)finalCounter * 100);
                        result.append(o.getKey()).append(": ").
                                append(o.getValue()).append(" time(s), ").
                                append(percents);
                    });
        }
        if (OUTFILE != null) {
            try (FileWriter fw = new FileWriter(OUTFILE)) {
                fw.write(String.valueOf(result));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.out.print(result);
        }
    }
}
