package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class FileUtils {

    public static List<String> getCarRegsFromFile(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // UK reg pattern: 2 letters + 2 numbers + space (optional) + 3 letters
        Pattern pattern = Pattern.compile("\\b[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}\\b");
        Matcher matcher = pattern.matcher(content);

        Set<String> registrations = new LinkedHashSet<>(); 
        while (matcher.find()) {
            registrations.add(matcher.group().replaceAll("\\s+", "")); // remove any space
        }

        return new ArrayList<>(registrations);
    }
}
