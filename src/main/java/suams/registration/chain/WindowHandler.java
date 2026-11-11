package suams.registration.chain;

import suams.registration.CourseRegistration;

/** Rule 1: registration is allowed only inside the registration window. */
public class WindowHandler extends RegistrationHandler {

    private final CourseRegistration rules = new CourseRegistration();

    @Override
    protected String check(RegistrationRequest request) {
        String status = rules.checkRegistrationDate(request.date());
        return status.equals("Registration Open") ? null : status;
    }
}
