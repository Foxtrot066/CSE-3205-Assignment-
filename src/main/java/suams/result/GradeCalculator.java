package suams.result;

/** Converts total marks (0-100) into a UGC letter grade. */
public final class GradeCalculator {

    private GradeCalculator() {
    }

    public static String getLetterGrade(double totalMarks) {
        if (totalMarks < 0 || totalMarks > 100) {
            throw new IllegalArgumentException("Invalid marks");
        }
        if (totalMarks >= 80) return "A+";
        if (totalMarks >= 75) return "A";
        if (totalMarks >= 70) return "A-";
        if (totalMarks >= 65) return "B+";
        if (totalMarks >= 60) return "B";
        if (totalMarks >= 55) return "B-";
        if (totalMarks >= 50) return "C+";
        if (totalMarks >= 45) return "C";
        if (totalMarks >= 40) return "D";
        return "F";
    }
}
