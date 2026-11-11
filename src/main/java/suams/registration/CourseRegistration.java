package suams.registration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Course Registration component of SUAMS. */
public class CourseRegistration {

    public static final LocalDate WINDOW_START = LocalDate.of(2027, 1, 5);
    public static final LocalDate WINDOW_END = LocalDate.of(2027, 1, 15);

    private final Map<String, List<String>> registeredCourses = new HashMap<>();

    /** Unit-tested function: true when two classes overlap on the same day. */
    public boolean hasTimeClash(ClassSlot a, ClassSlot b) {
        return a.day() == b.day()
                && a.start().isBefore(b.end())
                && b.start().isBefore(a.end());
    }

    /** Black-box tested function: registration is allowed from 5 to 15 January 2027. */
    public String checkRegistrationDate(LocalDate date) {
        if (date.isBefore(WINDOW_START)) {
            return "Registration Not Open Yet";
        }
        if (date.isAfter(WINDOW_END)) {
            return "Registration Closed";
        }
        return "Registration Open";
    }

    public void register(String studentId, String courseCode) {
        List<String> courses = registeredCourses.computeIfAbsent(studentId, k -> new ArrayList<>());
        if (!courses.contains(courseCode)) {
            courses.add(courseCode);
        }
    }

    public void drop(String studentId, String courseCode) {
        List<String> courses = registeredCourses.get(studentId);
        if (courses != null) {
            courses.remove(courseCode);
        }
    }

    public List<String> getRegisteredCourses(String studentId) {
        return List.copyOf(registeredCourses.getOrDefault(studentId, List.of()));
    }
}
