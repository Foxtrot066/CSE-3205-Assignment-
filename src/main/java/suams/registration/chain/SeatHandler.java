package suams.registration.chain;

/** Rule 5: the course section must still have a free seat. */
public class SeatHandler extends RegistrationHandler {

    @Override
    protected String check(RegistrationRequest request) {
        return request.seatsLeft() <= 0 ? "No Seat Available" : null;
    }
}
