package fundamentals;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.*;

public class StudentRepository {
    public static boolean isDataInitialized = false;
    public static HashMap<String, HashMap<String, ArrayList<Integer>>> studentsByCourse;

    public static void initializeData() {
        if (isDataInitialized) {
            System.out.println(ExceptionMessages.EXAMPLE_EXCEPTION_MESSAGE);
            return;
        }

        studentsByCourse = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        readData();
    }

    private static void readData() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("")) {
            String[] tokens = input.split("\\s+");
            String course = tokens[0];
            String student = tokens[1];
            Integer mark = Integer.parseInt(tokens[2]);

            if (!studentsByCourse.containsKey(course)) {
                studentsByCourse.put(course, new HashMap<>());
            }

            if (!studentsByCourse.get(course).containsKey(student)) {
                studentsByCourse.get(course).put(student, new ArrayList<>());
            }

            studentsByCourse.get(course).get(student).add(mark);

            isDataInitialized = true;
            OutputWriter.writeMessageOnNewLine("Data read.");
            input = scanner.nextLine();
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
        for (Map.Entry<String, ArrayList<Integer>> student : studentsByCourse.get(courseName).entrySet()) {
            OutputWriter.printStudent(student.getKey(), student.getValue());
        }
    }
 }
