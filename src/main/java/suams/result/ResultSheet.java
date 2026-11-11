package suams.result;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Result sheet of one course: marks are entered, approved and then published. */
public class ResultSheet {

    public enum Status { DRAFT, APPROVED, PUBLISHED }

    private final String courseCode;
    private final Map<String, Double> marks = new LinkedHashMap<>();
    private Status status = Status.DRAFT;

    public ResultSheet(String courseCode) {
        this.courseCode = courseCode;
    }

    public void enterMarks(String studentId, double totalMarks) {
        if (status == Status.PUBLISHED) {
            throw new IllegalStateException("Marks cannot be changed after publication");
        }
        marks.put(studentId, totalMarks);
    }

    public void approve() {
        status = Status.APPROVED;
    }

    public void publish() {
        if (status != Status.APPROVED) {
            throw new IllegalStateException("Approve the result before publishing");
        }
        status = Status.PUBLISHED;
    }

    public Status getStatus() {
        return status;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getStudentIds() {
        return List.copyOf(marks.keySet());
    }
}
