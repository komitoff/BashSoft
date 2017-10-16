package repository;

import static_data.ExceptionMessages;
import io.OutputWriter;
import static_data.SessionData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentRepository {
    public static boolean isDataInitialized = false;
    public static HashMap<String, HashMap<String, ArrayList<Integer>>> studentsByCourse;

    public static void initializeData(String fileName) throws IOException {
        if (isDataInitialized) {
            System.out.println(ExceptionMessages.EXAMPLE_EXCEPTION_MESSAGE);
            return;
        }

        studentsByCourse = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        readData(fileName);
    }

    private static void readData(String fileName) throws IOException {
        String regex = "([A-Z][a-zA-Z#+]*_[A-Z][a-z]{2}_\\d{4})\\s+([A-Z][a-z]{0,3}\\d{2}_\\d{2,4})\\s+(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            matcher = pattern.matcher(line);

            if (!line.isEmpty() && matcher.find()) {
                String course = matcher.group(1);
                String student = matcher.group(2);
                Integer mark = Integer.parseInt(matcher.group(3));

                if (!studentsByCourse.containsKey(course)) {
                    studentsByCourse.put(course, new HashMap<>());
                }

                if (!studentsByCourse.get(course).containsKey(student)) {
                    studentsByCourse.get(course).put(student, new ArrayList<>());
                }

                studentsByCourse.get(course).get(student).add(mark);

                isDataInitialized = true;
                OutputWriter.writeMessageOnNewLine("Data read.");
            }
        }
    }

    public static boolean isQueryForCoursePossible(String courseName) {
        if (!isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }
        return  true;
    }

    public static boolean isQueryForStudentPossible(String courseName, String studentName) {
        if (!isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }

        if (!studentsByCourse.containsKey(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return false;
        }

        return true;
    }

    public static void getStudentMarksInCourse(String course, String studentName) {
        if (!isQueryForStudentPossible(studentName, course)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_STUDENT);
            return;
        }

        List<Integer> marks = studentsByCourse.get(course).get(studentName);
        OutputWriter.printStudent(studentName, marks);
    }

    public static void getStudentsByCourse(String courseName) {
        if (!isQueryForCoursePossible(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return;
        }

        OutputWriter.writeMessageOnNewLine(courseName + ":");
        if (studentsByCourse.containsKey(courseName)) {
            for (Map.Entry<String, ArrayList<Integer>> student : studentsByCourse.get(courseName).entrySet()) {
                OutputWriter.printStudent(student.getKey(), student.getValue());
            }
        }
        else {
            OutputWriter.writeMessageOnNewLine(ExceptionMessages.NO_SUCH_COURSE_FOUND);
        }
    }

    public static void printFilteredStudents(String course, String filter, Integer numberOfStudents) {
        if (!isQueryForCoursePossible(course)) {
            return;
        }

        if (numberOfStudents == null) {
            numberOfStudents = studentsByCourse.get(course).size();
        }

        RepositoryFilters.printFilteredStudents(studentsByCourse.get(course), filter, numberOfStudents);
    }

    public static void printOrderedStudents(String course, String compareType, Integer numberOfStudents) {
        if (!isQueryForCoursePossible(course)) {
            return;
        }

        if (numberOfStudents == null) {
            numberOfStudents = studentsByCourse.get(course).size();
        }

        RepositorySorters.printSortedStudents(studentsByCourse.get(course), compareType, numberOfStudents);
    }
 }
