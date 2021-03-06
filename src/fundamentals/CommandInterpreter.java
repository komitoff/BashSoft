package fundamentals;

import io.IOManager;
import io.InputReader;
import io.OutputWriter;
import judge.Tester;
import network.DownloadManager;
import repository.StudentRepository;
import static_data.SessionData;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CommandInterpreter {

    public static void interpretCommand(String input) throws IOException {
        String[] data = input.split("\\s+");
        String command = data[0];
        switch (command) {
            case "show" :
                tryShowWantedCourse(input, data);
                break;
            case "mkdir":
                tryCreateDirectory(input, data);
                break;
            case "ls":
                tryTraverseDirectory(input, data);
                break;
            //must be checked
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
                tryDownloadFile(input, data);
                break;
            case "downloadAsync":
                tryDownloadFileOnNewThread(input, data);
                break;
            case "help":
                tryGetHelpMethods(input, data);
                break;
            case "open":
                tryOpenFile(input, data);
                break;
            default:
                displayInvalidCommand(input);
                break;
        }
    }

    private static void tryGetHelpMethods(String input, String[] data) throws IOException {
        if (data.length != 1) {
            displayInvalidCommand(input);
            return;
        }

        OutputWriter.writeMessageOnNewLine("mkdir path - make directory");
        OutputWriter.writeMessageOnNewLine("ls depth - traverse directory");
        OutputWriter.writeMessageOnNewLine("cmp path1 path2 - compare two files");
        OutputWriter.writeMessageOnNewLine("changeDirRel relativePath - change directory");
        OutputWriter.writeMessageOnNewLine("changeDir absolutePath - change directory");
        OutputWriter.writeMessageOnNewLine("readDb path - read students data base");
        OutputWriter.writeMessageOnNewLine("filterExcelent - filter excelent students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterExcelent path - filter excelent students (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("filterAverage - filter average students (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("filterAverage path - filter average students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("filterPoor - filter low grade students (the output is on the console)");
        OutputWriter.writeMessageOnNewLine("filterPoor path - filter low grade students (the output is written in a file)");
        OutputWriter.writeMessageOnNewLine("order - sort students in increasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("order path - sort students in increasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("decOrder - sort students in decreasing order (the output is written on the console)");
        OutputWriter.writeMessageOnNewLine("decOrder path - sort students in decreasing order (the output is written in a given path)");
        OutputWriter.writeMessageOnNewLine("download pathOfFile - download file (saved in current directory)");
        OutputWriter.writeMessageOnNewLine("downloadAsync path - download file asynchronously (save in the current directory)");
        OutputWriter.writeMessageOnNewLine("help - get help");
        OutputWriter.writeEmptyLine();
    }

    private static void tryDownloadFileOnNewThread(String input, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }

        String fileUrl = data[1];
        DownloadManager.downloadOnNewThread(fileUrl);
    }

    private static void tryDownloadFile(String input, String[] data) {
        if (data.length != 2) {
            displayInvalidCommand(input);
            return;
        }

        String fileUrl = data[1];
        DownloadManager.download(fileUrl);
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
