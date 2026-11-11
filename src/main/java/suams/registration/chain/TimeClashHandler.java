package suams.registration.chain;

import suams.registration.ClassSlot;
import suams.registration.CourseRegistration;

/** Rule 2: the new course must not clash with an already registered course. */
public class TimeClashHandler extends RegistrationHandler {

    private final CourseRegistration rules = new CourseRegistration();

    @Override
    protected String check(RegistrationRequest request) {
        for (ClassSlot registered : request.registeredClasses()) {
            if (rules.hasTimeClash(request.newClass(), registered)) {
                return "Time Clash with " + registered.courseCode();
            }
        }
        return null;
    }
}
