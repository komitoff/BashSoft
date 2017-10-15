package fundamentals;

import java.util.*;

public class RepositorySorters {

    public static void printSortedStudents (
            HashMap<String, ArrayList<Integer>> courseData,
            String comparisonType,
            Integer numberOfStudents
    ) {
        Comparator<Map.Entry<String, ArrayList<Integer>>> comparator = createComparator(comparisonType);
        ArrayList<Map.Entry<String, ArrayList<Integer>>> sortedStudents = new ArrayList<>();
        sortedStudents.addAll(courseData.entrySet());

        Collections.sort(sortedStudents, comparator);
        int studentCount = 0;
        for (Map.Entry<String, ArrayList<Integer>> student : sortedStudents) {
            if (studentCount == numberOfStudents) {
                break;
            }
            OutputWriter.printStudent(student.getKey(), student.getValue());
            studentCount ++;
        }
    }

    private static Comparator<Map.Entry<String,ArrayList<Integer>>> createComparator(String comparisonType) {
        switch (comparisonType) {
            case "ascending":
                return new Comparator<Map.Entry<String, ArrayList<Integer>>>() {
                    @Override
                    public int compare(Map.Entry<String, ArrayList<Integer>> firstStudent,
                                       Map.Entry<String, ArrayList<Integer>> secondStudent) {
                        Double firstStudentTotal = getTotalScore(firstStudent.getValue());
                        Double secondStudentTotel = getTotalScore(secondStudent.getValue());

                        return firstStudentTotal.compareTo(secondStudentTotel);
                    }
                };
            case "descending":
                return new Comparator<Map.Entry<String, ArrayList<Integer>>>() {
                    @Override
                    public int compare(Map.Entry<String, ArrayList<Integer>> firstStudent,
                                       Map.Entry<String, ArrayList<Integer>> secondStudent) {
                        Double firstStudentTotal = getTotalScore(firstStudent.getValue());
                        Double secondStudentTotel = getTotalScore(secondStudent.getValue());

                        return secondStudentTotel.compareTo(firstStudentTotal);
                    }
                };
            default:
                return null;
        }
    }

    private static Double getTotalScore(ArrayList<Integer> value) {
        Double total = 0d;
        for (int i = 0; i < value.size(); i++) {
            total += value.get(i);
        }

        return total / value.size();
    }
}
