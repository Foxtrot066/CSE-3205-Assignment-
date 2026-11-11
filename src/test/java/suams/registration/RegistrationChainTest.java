package suams.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import suams.registration.chain.RegistrationRequest;
import suams.registration.chain.RegistrationService;

/** Unit tests for the Chain of Responsibility used for registration rules (Section 3.1). */
class RegistrationChainTest {

    private final RegistrationService service = new RegistrationService();

    private static final ClassSlot CSE3207 = ClassSlot.of("CSE 3207", DayOfWeek.SUNDAY, "10:30", "11:20");

    private static RegistrationRequest request(ClassSlot newClass, LocalDate date) {
        return new RegistrationRequest("2203001", newClass, 3.0, date, List.of(CSE3207), 15.0, false, 5);
    }

    @Test
    void u07_validRequestPassesAllFiveHandlers() {
        ClassSlot cse3205 = ClassSlot.of("CSE 3205", DayOfWeek.MONDAY, "10:00", "10:50");
        assertEquals("Registration Successful", service.register(request(cse3205, LocalDate.of(2027, 1, 10))));
    }

    @Test
    void u08_clashingClassIsStoppedByTimeClashHandler() {
        ClassSlot cse3205 = ClassSlot.of("CSE 3205", DayOfWeek.SUNDAY, "10:00", "10:50");
        assertEquals("Time Clash with CSE 3207", service.register(request(cse3205, LocalDate.of(2027, 1, 10))));
    }

    @Test
    void u09_lateRequestIsStoppedByTheFirstHandler() {
        ClassSlot cse3205 = ClassSlot.of("CSE 3205", DayOfWeek.MONDAY, "10:00", "10:50");
        assertEquals("Registration Closed", service.register(request(cse3205, LocalDate.of(2027, 1, 20))));
    }

    // ---------- Loop testing of the loop in TimeClashHandler.check() (Section 2.5) ----------

    private static final ClassSlot NEW_CLASS = ClassSlot.of("CSE 3205", DayOfWeek.MONDAY, "10:00", "10:50");
    private static final ClassSlot CLASHING = ClassSlot.of("CSE 3209", DayOfWeek.MONDAY, "10:30", "11:20");

    /** k registered classes that never clash with NEW_CLASS (none of them is on Monday). */
    private static List<ClassSlot> freeClasses(int k) {
        DayOfWeek[] days = {DayOfWeek.SUNDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY};
        List<ClassSlot> slots = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int hour = 8 + i / 4;
            slots.add(ClassSlot.of("CSE 32" + (11 + i), days[i % 4],
                    String.format("%02d:00", hour), String.format("%02d:50", hour)));
        }
        return slots;
    }

    private String registerWith(List<ClassSlot> registered) {
        return service.register(new RegistrationRequest("2203001", NEW_CLASS, 3.0, LocalDate.of(2027, 1, 10),
                registered, 3.0 * registered.size(), false, 5));
    }

    @Test
    void l01_loopSkippedWhenNothingIsRegistered() {
        assertEquals("Registration Successful", registerWith(freeClasses(0)));
    }

    @Test
    void l02_onePassWithoutClash() {
        assertEquals("Registration Successful", registerWith(freeClasses(1)));
    }

    @Test
    void l03_twoPassesClashFoundOnSecond() {
        List<ClassSlot> registered = new ArrayList<>(freeClasses(1));
        registered.add(CLASHING);
        assertEquals("Time Clash with CSE 3209", registerWith(registered));
    }

    @Test
    void l04_mPassesWithoutClash() {
        assertEquals("Registration Successful", registerWith(freeClasses(4)));
    }

    @Test
    void l05_nPassesClashFoundOnLast() {
        List<ClassSlot> registered = new ArrayList<>(freeClasses(7));
        registered.add(CLASHING);
        assertEquals("Time Clash with CSE 3209", registerWith(registered));
    }
}
