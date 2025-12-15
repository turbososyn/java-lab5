package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextFileAnalyzer {
    public String lineWithMaxWords(String path) throws IOException {
        String bestLine = null;
        int bestCount = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                int count = countWords(line);
                if (count > bestCount) {
                    bestCount = count;
                    bestLine = line;
                }
            }
        }
        return bestLine;
    }

    private int countWords(String line) {
        String t = line.trim();
        if (t.isEmpty()) return 0;
        return t.split("\\s+").length;
    }
}
