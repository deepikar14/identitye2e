package tests;

import base.BaseTest;
import pages.LoginPage;
import utils.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LoginTest extends BaseTest {

    @Test
    public void testCompareDynamicInputOutputPairs() throws Exception {
        File resourceDir = new File("src/test/resources");

        // Find all matching car_input_*.txt and car_output_*.txt files
        File[] inputFiles = resourceDir.listFiles((dir, name) -> name.startsWith("car_input_") && name.endsWith(".txt"));
        File[] outputFiles = resourceDir.listFiles((dir, name) -> name.startsWith("car_output_") && name.endsWith(".txt"));

        if (inputFiles == null || outputFiles == null) {
            throw new FileNotFoundException("Missing resource files.");
        }

        
        Map<String, File> inputMap = new HashMap<>();
        Map<String, File> outputMap = new HashMap<>();

        for (File f : inputFiles) {
            String suffix = f.getName().replace("car_input_", "").replace(".txt", "");
            inputMap.put(suffix, f);
        }
        for (File f : outputFiles) {
            String suffix = f.getName().replace("car_output_", "").replace(".txt", "");
            outputMap.put(suffix, f);
        }

        List<String> commonSuffixes = inputMap.keySet().stream()
                .filter(outputMap::containsKey)
                .sorted()
                .collect(Collectors.toList());

        if (commonSuffixes.isEmpty()) {
            throw new IllegalStateException("No matching input/output file pairs found.");
        }

        List<String> allErrors = new ArrayList<>();

        for (String suffix : commonSuffixes) {
            File inputFile = inputMap.get(suffix);
            File outputFile = outputMap.get(suffix);
            String reportPath = "target/car_report_" + suffix + ".csv";

            System.out.println("\n===== Comparing: " + inputFile.getName() + " vs " + outputFile.getName() + " =====");

            List<String> regNumbers = FileUtils.getCarRegsFromFile(inputFile.getPath());
            Map<String, Map<String, String>> expectedMap = loadExpectedCarDetails(outputFile.getPath());
            writeCsvHeader(reportPath);

            for (String reg : regNumbers) {
                System.out.println(">>> Testing registration: " + reg);
                driver.get("https://motorway.com");

                LoginPage loginPage = new LoginPage(driver);
                loginPage.enterRegistrationNumber(reg);
                loginPage.clickValueYourCar();

                String actualYear = loginPage.getYear();
                String actualMakeModel = loginPage.getMakeModel();

                
                String formattedReg = reg.replaceAll("\\s+", "").toUpperCase();

                String result;
                String expectedYear = "-";
                String expectedMakeModel = "-";

                if (!expectedMap.containsKey(formattedReg)) {
                    result = "FAIL";
                    String msg = "FAIL: No expected data for " + reg + " in " + inputFile.getName();
                    System.out.println(">>> " + msg);
                    allErrors.add(msg);
                } else {
                    Map<String, String> expected = expectedMap.get(formattedReg);
                    expectedYear = expected.get("year");
                    expectedMakeModel = expected.get("make") + " " + expected.get("model");

                    boolean yearMatches = actualYear.equalsIgnoreCase(expectedYear);
                    boolean makeModelMatches = actualMakeModel.equalsIgnoreCase(expectedMakeModel);
                    result = (yearMatches && makeModelMatches) ? "PASS" : "FAIL";

                    System.out.println(">>> Expected Year: " + expectedYear);
                    System.out.println(">>> Actual Year:   " + actualYear);
                    System.out.println(">>> Expected Make & Model: " + expectedMakeModel);
                    System.out.println(">>> Actual Make & Model:   " + actualMakeModel);

                    if (!result.equals("PASS")) {
                        String msg = "FAIL: Mismatch for " + reg + " in " + inputFile.getName();
                        System.out.println(">>> " + msg);
                        allErrors.add(msg);
                    } else {
                        System.out.println("PASS: Match for " + reg);
                    }
                }

                writeCsvRow(reportPath, reg, expectedYear, actualYear, expectedMakeModel, actualMakeModel, result);
            }

            System.out.println("CSV report saved to: " + reportPath);
        }

        if (!allErrors.isEmpty()) {
            System.out.println("\n========= Test Summary =========");
            allErrors.forEach(System.out::println);
            Assert.fail("Test failed for one or more registrations:\n" + String.join("\n", allErrors));
        }
    }

    private Map<String, Map<String, String>> loadExpectedCarDetails(String filePath) throws Exception {
        Map<String, Map<String, String>> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine(); 

        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",", -1);
            if (tokens.length >= 4) {
                
                String reg = tokens[0].replaceAll("\\s+", "").toUpperCase();
                String make = tokens[1].trim();
                String model = tokens[2].trim();
                String year = tokens[3].trim();

                Map<String, String> details = new HashMap<>();
                details.put("make", make);
                details.put("model", model);
                details.put("year", year);
                map.put(reg, details);
            }
        }

        br.close();
        return map;
    }

    private void writeCsvHeader(String filePath) throws Exception {
        FileWriter fw = new FileWriter(filePath);
        fw.write("Registration,Expected Year,Actual Year,Expected Make & Model,Actual Make & Model,Result\n");
        fw.close();
    }

    private void writeCsvRow(String filePath, String reg, String expectedYear, String actualYear,
                             String expectedMakeModel, String actualMakeModel, String result) throws Exception {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(String.format("%s,%s,%s,%s,%s,%s\n",
                reg, expectedYear, actualYear, expectedMakeModel, actualMakeModel, result));
        fw.close();
    }
}
