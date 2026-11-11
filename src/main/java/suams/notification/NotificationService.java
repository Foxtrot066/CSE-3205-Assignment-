package suams.notification;

/** Academic Notification module (SMS / e-mail / dashboard). */
public interface NotificationService {

    void send(String studentId, String message);
}
