package fundamentals;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void compareContent(String actualOutput, String expectedOutput) throws IOException {
        OutputWriter.writeMessageOnNewLine("Loading files...");
        String mismatchPath = getMismatchPath(expectedOutput);
        List<String> actualOutputStrings = readTextFile(actualOutput);
        List<String> expectedOutputStrings = readTextFile(expectedOutput);
        boolean mismatch = compareStrings(actualOutputStrings, expectedOutputStrings, mismatchPath);
        if (mismatch) {
            List<String> mismatchingString = readTextFile(mismatchPath);
            mismatchingString.forEach(OutputWriter::writeMessageOnNewLine);
        } else {
            OutputWriter.writeMessageOnNewLine("Files are identical. No mismatches");
        }
    }

    public static List<String> readTextFile(String filePath) throws IOException {
        List<String> text = new ArrayList<>();

        try {
            File file = new File(filePath);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();
            while (!line.equals("")) {
                text.add(line);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception e) {
            OutputWriter.displayException(e.getMessage());
        }
        return text;
    }

    private static boolean compareStrings (
            List<String> actualOutputStrings,
            List<String> expectedOutputStrings,
            String mismatchPath) {
        OutputWriter.writeMessageOnNewLine("Comparing files...");
        String output = "";
        boolean isMismatch = false;

        try {
            FileWriter fileWriter = new FileWriter(mismatchPath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (int i = 0; i < expectedOutputStrings.size(); i++) {
                String actualLine = actualOutputStrings.get(i);
                String expectedLine = expectedOutputStrings.get(i);

                if (!actualLine.equals(expectedLine)) {
                    output = String.format("mismatch -> expected{%s}, actual{%s}%n",
                            expectedLine, actualLine);
                    isMismatch = true;
                } else {
                    output = String.format("line match -> %s%n", actualLine);
                }

                if (isMismatch) {
                    writer.write(output);
                }
            }
            writer.close();
        }
        catch (Exception e) {
            OutputWriter.displayException(e.getMessage());
        }

        return isMismatch;
    }

    private static String getMismatchPath(String expectedOutput) {
        int index = expectedOutput.indexOf('\\');
        String directoryPath = expectedOutput.substring(0, index);
        return directoryPath + "\\mismatch.txt";
    }
}
