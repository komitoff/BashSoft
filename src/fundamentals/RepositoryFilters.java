package fundamentals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class RepositoryFilters {
    public static void printFilteredStudents(
        HashMap<String, ArrayList<Integer>> courseData,
        String filterType,
        Integer numberOfStudents
    ) {
        Predicate<Double> filter = createFilter(filterType);
        if (filter == null) {
            OutputWriter.writeMessageOnNewLine(ExceptionMessages.INVALID_FILTER);
            return;
        }

        int studentCount = 0;
        for (String student : courseData.keySet()) {
            if (studentCount == numberOfStudents) {
                break;
            }

            ArrayList<Integer> marks = courseData.get(student);
            Double averageMark = getStudentAverageGrade(marks);
            if (filter.test(averageMark)) {
                OutputWriter.printStudent(student, marks);
                studentCount ++;
            }
        }
    }

    private static Predicate<Double> createFilter(String filterType) {
        switch (filterType) {
            case "excellent":
                return mark -> mark >= 5.00;
            case "average":
                return mark -> 3.5 <= mark && mark < 5.00;
            case "poor":
                return mark -> mark < 3.5;
            default:
                return null;
        }
    }

    private static Double getStudentAverageGrade(ArrayList<Integer> grades) {
        Double totalScore = 0d;
        for (int i = 0; i < grades.size(); i++) {
            totalScore += grades.get(i);
        }

        Double percentage = totalScore / (grades.size() * 100);
        return (percentage * 4) + 2;
    }
}
