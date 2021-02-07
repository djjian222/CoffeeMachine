package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Function to convert the given input file into string
     * @param path
     * @return
     */
    public static String getFileAsString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            List<String> content = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                content.add(line);
            }
            return content.stream().collect(Collectors.joining("\n"));
        }catch (Exception e) {
            return "";
        }
    }

}
