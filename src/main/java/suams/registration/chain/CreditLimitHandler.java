package suams.registration.chain;

/** Rule 3: total credits after registration must not exceed 21. */
public class CreditLimitHandler extends RegistrationHandler {

    public static final double MAX_CREDITS = 21.0;

    @Override
    protected String check(RegistrationRequest request) {
        if (request.currentCredits() + request.courseCredits() > MAX_CREDITS) {
            return "Credit Limit Exceeded";
        }
        return null;
    }
}
