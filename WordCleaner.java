import java.io.*;
import java.util.*;

public class WordCleaner {
    public static String[] getWords(String filename) throws IOException {
        Set<String> wordSet = new LinkedHashSet<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            // Split on anything that isn't a letter
            String[] tokens = line.split("[^a-zA-Z]+");
            for (String word : tokens) {
                if (!word.isEmpty())
                    wordSet.add(word.toLowerCase());
            }
        }
        br.close();
        return wordSet.toArray(new String[0]);
    }
}
