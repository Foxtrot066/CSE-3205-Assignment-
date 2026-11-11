package suams.registration;

import java.util.List;

/** Examination Form module: the form is filled from the student's registered courses. */
public class ExamFormService {

    private final CourseRegistration registration;

    public ExamFormService(CourseRegistration registration) {
        this.registration = registration;
    }

    public List<String> createExamForm(String studentId) {
        return registration.getRegisteredCourses(studentId);
    }
}
