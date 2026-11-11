package suams.registration;

import java.time.DayOfWeek;
import java.time.LocalTime;

/** Weekly class time of a course, for example Sunday 10:00-10:50. */
public record ClassSlot(String courseCode, DayOfWeek day, LocalTime start, LocalTime end) {

    public static ClassSlot of(String courseCode, DayOfWeek day, String start, String end) {
        return new ClassSlot(courseCode, day, LocalTime.parse(start), LocalTime.parse(end));
    }
}
