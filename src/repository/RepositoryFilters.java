package repository;

import static_data.ExceptionMessages;
import io.OutputWriter;

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
//            Double averageMark = getStudentAverageGrade(marks);
            Double averageMark = marks.stream()
                    .mapToInt(Integer::valueOf)
                    .average()
                    .getAsDouble();

            Double percentageOfFulfilment = averageMark / 100;
            Double mark = percentageOfFulfilment * 4 + 2;

            if (filter.test(mark)) {
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
}
