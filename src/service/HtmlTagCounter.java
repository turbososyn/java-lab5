package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagCounter {
    private static final Pattern TAG = Pattern.compile("<\\s*([a-zA-Z][a-zA-Z0-9]*)\\b");

    public Map<String, Integer> countTags(String url) throws Exception {
        String html = fetch(url);
        Matcher m = TAG.matcher(html);

        Map<String, Integer> freq = new HashMap<>();
        while (m.find()) {
            String tag = m.group(1).toLowerCase();
            freq.put(tag, freq.getOrDefault(tag, 0) + 1);
        }
        return freq;
    }

    public void printLexicographic(Map<String, Integer> tags) {
        TreeMap<String, Integer> sorted = new TreeMap<>(tags);
        for (var e : sorted.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }
    }

    public void printByFrequency(Map<String, Integer> tags) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(tags.entrySet());
        list.sort((a, b) -> {
            int c = Integer.compare(a.getValue(), b.getValue());
            if (c != 0) return c;
            return a.getKey().compareTo(b.getKey());
        });

        for (var e : list) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }
    }

    private String fetch(String url) throws Exception {
        URL u = new URL(url);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(u.openStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            return sb.toString();
        }
    }
}
