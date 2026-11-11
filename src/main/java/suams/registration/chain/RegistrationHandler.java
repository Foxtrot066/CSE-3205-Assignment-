package suams.registration.chain;

/**
 * Handler of the Chain of Responsibility pattern (Section 3.1).
 * Each concrete handler checks one registration rule. If the rule fails, the
 * chain stops and the reason is returned; otherwise the request is passed on.
 */
public abstract class RegistrationHandler {

    private RegistrationHandler next;

    /** Links the next handler and returns it, so a chain can be built in one line. */
    public RegistrationHandler linkWith(RegistrationHandler next) {
        this.next = next;
        return next;
    }

    public String handle(RegistrationRequest request) {
        String problem = check(request);
        if (problem != null) {
            return problem;                        // stop the chain
        }
        if (next == null) {
            return "Registration Successful";      // end of chain: every rule passed
        }
        return next.handle(request);               // pass the request on
    }

    /** Returns null when the rule passes, or a rejection message when it fails. */
    protected abstract String check(RegistrationRequest request);
}
