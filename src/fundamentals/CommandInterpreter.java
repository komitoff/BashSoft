package fundamentals;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class CommandInterpreter {

    public static void interpretCommand(String input) throws IOException {
        String[] data = input.split(" ");
        String command = data[0];
        switch (command) {
            case "show" :
                tryShowWantedCourse(input, data);
                break;
            case "mkdir":
                tryCreateDirectory(input, data);
                break;
            case "Is":
                tryTraverseDirectory(input, data);
                break;
            case "cmp":
                tryCompareFiles(input, data);
                break;
            case "changeDirRel":
                tryChangeRelativePath(input, data);
                break;
            case "changeDirAbs":
                tryChangeAbsolutePath(input, data);
                break;
            case "readDb":
                tryReadDatabaseFromFile(input, data);
                break;
            case "filter":
                tryPrintFilteredStudents(input, data);
                break;
            case "order":
                tryPrintOrderedStudents(input, data);
                break;
            case "download":
                break;
            case "downloadAsync":
                break;
            case "help":
                //TODO: implement commands
                break;
            case "open":
                tryOpenFile(input, data);
                break;
            default:
                displayInvalidCommand(input);
                break;
        }
    }

    private static void tryPrintFilteredStudents(String input, String[] data) {
        if (data.length != 3 && data.length != 4) {
            displayInvalidCommand(input);
        }

        String course = data[1];
        String filter = data[2];

        if (data.length == 3) {
            StudentRepository.printFilteredStudents(course, filter, null);
        }

        if (data.length == 4) {
            Integer numberOfStudents = Integer.valueOf(data[3]);
            StudentRepository.printFilteredStudents(course, filter, numberOfStudents);
        }
    }

    private static void tryPrintOrderedStudents(String input, String[] data) {
        if (data.length != 3 && data.length != 4) {
            displayInvalidCommand(input);
            return;
        }

        String course = data[1];
        String compareType = data[2];
        if (data.length == 3) {
            StudentRepository.printOrderedStudents(course, compareType, null);
            return;
        }

        if (data.length == 4) {
            Integer numberOfStudents = Integer.valueOf(data[3]);
            StudentRepository.printOrderedStudents(course, compareType, numberOfStudents);
            return;
        }
    }

    private static void tryShowWantedCourse(String input, String[] data) {
        if (data.length != 2 && data.length != 3) {
            displayInvalidCommand(input);
            return;
        }

        if (data.length == 2) {
            String courseName = data[1];
            StudentRepository.getStudentsByCourse(courseName);
        }

        if (data.length == 3) {
            String courseName = data[1];
            String studentName = data[2];
            StudentRepository.getStudentMarksInCourse(courseName, studentName);
        }
    }

    private static void tryTraverseDirectory(String input, String[] data) {
        if (data.length != 1 && data.length != 2) {
            displayInvalidCommand(input);
            return;
        }

        if (data.length == 1) {
            IOManager.traverseDirectory(0);
        }

        if (data.length == 2) {
            IOManager.traverseDirectory(Integer.parseInt(data[1]));
        }
    }

    private static void tryCompareFiles(String input, String[] data) {
        if (data.length != 3) {
            displayInvalidCommand(input);
            return;
        }

        String firstPath = data[1];
        String secondPath = data[2];
        try {
            Tester.compareContent(firstPath, secondPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tryChangeRelativePath(String input, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }

        String relativePath = data[1];
        IOManager.changeCurrentDirRelativePath(relativePath);
    }

    private static void tryChangeAbsolutePath(String input, String[] data) {
        if (data.length != 2 ) {
            displayInvalidCommand(input);
            return;
        }

        String absolutePath = data[1];
        IOManager.changeCurrentDirAbsolute(absolutePath);
    }

    private static void initializeData(String fileName) {

    }

    private static void readData(String fileName) throws IOException {
        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
        }
    }
    private static void tryCreateDirectory(String input, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }
        IOManager.createDirectoryInCurrentFolder(data[1]);
    }

    private static void tryReadDatabaseFromFile (String input, String[] data) throws IOException {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }

        String fileName = data[1];
        StudentRepository.initializeData(fileName);
    }

    private static void tryOpenFile(String input, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }
        String fullFilePath = SessionData.currentPath + "\\" + data[1];
        File f = new File(fullFilePath);
        try {
            Desktop.getDesktop().open(f);
        } catch (IOException e) {
            OutputWriter.displayException(String.format("Unable to open file %s.", fullFilePath));
        }
    }

    private static void displayInvalidCommand(String input) {
        OutputWriter.writeMessageOnNewLine(String.format("The command %s is invalid.", input));
    }
}
