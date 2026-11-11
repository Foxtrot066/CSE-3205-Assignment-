package suams.registration.chain;

/** Rule 4: a student with unpaid dues cannot register. */
public class DuesHandler extends RegistrationHandler {

    @Override
    protected String check(RegistrationRequest request) {
        return request.hasUnpaidDues() ? "Clear Dues First" : null;
    }
}
