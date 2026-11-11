package suams.result;

import java.util.ArrayList;
import java.util.List;
import suams.notification.NotificationService;

/** Publishes an approved result sheet and notifies every student on it. */
public class ResultPublisher {

    private final NotificationService notifier;
    private final List<String> retryQueue = new ArrayList<>();

    public ResultPublisher(NotificationService notifier) {
        this.notifier = notifier;
    }

    public void publish(ResultSheet sheet) {
        sheet.publish();
        for (String studentId : sheet.getStudentIds()) {
            try {
                notifier.send(studentId, "Result of " + sheet.getCourseCode() + " has been published");
            } catch (RuntimeException e) {
                retryQueue.add(studentId);   // delivered later; the result stays published
            }
        }
    }

    public List<String> getRetryQueue() {
        return List.copyOf(retryQueue);
    }
}
