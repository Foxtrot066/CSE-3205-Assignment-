package suams.registration.chain;

import java.time.LocalDate;
import java.util.List;
import suams.registration.ClassSlot;

/** Everything the handlers need to decide whether a course can be registered. */
public record RegistrationRequest(
        String studentId,
        ClassSlot newClass,
        double courseCredits,
        LocalDate date,
        List<ClassSlot> registeredClasses,
        double currentCredits,
        boolean hasUnpaidDues,
        int seatsLeft) {
}
