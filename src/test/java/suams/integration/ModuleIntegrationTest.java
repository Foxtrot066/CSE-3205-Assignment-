package suams.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import suams.notification.NotificationService;
import suams.registration.CourseRegistration;
import suams.registration.ExamFormService;
import suams.result.ResultPublisher;
import suams.result.ResultSheet;

/**
 * Thread-based integration tests (Section 2.2).
 * Thread 1: Course Registration -> Examination Form.
 * Thread 2: Result Publication -> Academic Notification (replaced by stubs).
 */
class ModuleIntegrationTest {

    /** Stub that records every message instead of sending a real SMS or e-mail. */
    static class RecordingNotificationStub implements NotificationService {
        final List<String> receivers = new ArrayList<>();

        @Override
        public void send(String studentId, String message) {
            receivers.add(studentId);
        }
    }

    /** Stub that behaves like a notification server that is down. */
    static class FailingNotificationStub implements NotificationService {
        @Override
        public void send(String studentId, String message) {
            throw new IllegalStateException("Notification server unavailable");
        }
    }

    private static ResultSheet approvedSheet() {
        ResultSheet sheet = new ResultSheet("CSE 3205");
        sheet.enterMarks("2203001", 72);
        sheet.enterMarks("2203002", 55);
        sheet.enterMarks("2203003", 38);
        sheet.approve();
        return sheet;
    }

    @Test
    void i01_examFormListsExactlyTheRegisteredCourses() {
        CourseRegistration registration = new CourseRegistration();
        registration.register("2203001", "CSE 3205");
        registration.register("2203001", "CSE 3207");
        registration.register("2203001", "CSE 3209");
        ExamFormService examForm = new ExamFormService(registration);
        assertEquals(List.of("CSE 3205", "CSE 3207", "CSE 3209"), examForm.createExamForm("2203001"));
    }

    @Test
    void i02_droppedCourseDisappearsFromExamForm() {
        CourseRegistration registration = new CourseRegistration();
        registration.register("2203001", "CSE 3205");
        registration.register("2203001", "CSE 3207");
        registration.register("2203001", "CSE 3209");
        registration.drop("2203001", "CSE 3209");
        ExamFormService examForm = new ExamFormService(registration);
        assertEquals(List.of("CSE 3205", "CSE 3207"), examForm.createExamForm("2203001"));
    }

    @Test
    void i03_publishingNotifiesEveryStudentOnce() {
        RecordingNotificationStub stub = new RecordingNotificationStub();
        ResultSheet sheet = approvedSheet();
        new ResultPublisher(stub).publish(sheet);
        assertEquals(ResultSheet.Status.PUBLISHED, sheet.getStatus());
        assertEquals(List.of("2203001", "2203002", "2203003"), stub.receivers);
    }

    @Test
    void i04_resultStaysPublishedWhenNotificationServerIsDown() {
        ResultSheet sheet = approvedSheet();
        ResultPublisher publisher = new ResultPublisher(new FailingNotificationStub());
        publisher.publish(sheet);
        assertEquals(ResultSheet.Status.PUBLISHED, sheet.getStatus());
        assertEquals(3, publisher.getRetryQueue().size());
    }
}
