package suams.registration.chain;

/** Client of the chain: builds it once and sends every registration request to its first handler. */
public class RegistrationService {

    private final RegistrationHandler chain;

    public RegistrationService() {
        chain = new WindowHandler();
        chain.linkWith(new TimeClashHandler())
             .linkWith(new CreditLimitHandler())
             .linkWith(new DuesHandler())
             .linkWith(new SeatHandler());
    }

    public String register(RegistrationRequest request) {
        return chain.handle(request);
    }
}
