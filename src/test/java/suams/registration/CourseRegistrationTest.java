package suams.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Unit tests (U) and equivalence partitioning tests (B) for Course Registration. */
class CourseRegistrationTest {

    private final CourseRegistration registration = new CourseRegistration();

    // ---------- Unit tests: hasTimeClash() (Section 2.1) ----------

    @Test
    void u01_overlappingClassesOnSameDayClash() {
        ClassSlot a = ClassSlot.of("CSE 3205", DayOfWeek.SUNDAY, "10:00", "10:50");
        ClassSlot b = ClassSlot.of("CSE 3207", DayOfWeek.SUNDAY, "10:30", "11:20");
        assertTrue(registration.hasTimeClash(a, b));
    }

    @Test
    void u02_backToBackClassesDoNotClash() {
        ClassSlot a = ClassSlot.of("CSE 3205", DayOfWeek.SUNDAY, "10:00", "10:50");
        ClassSlot b = ClassSlot.of("CSE 3209", DayOfWeek.SUNDAY, "10:50", "11:40");
        assertFalse(registration.hasTimeClash(a, b));
    }

    @Test
    void u03_sameTimeOnDifferentDaysDoesNotClash() {
        ClassSlot a = ClassSlot.of("CSE 3205", DayOfWeek.SUNDAY, "10:00", "10:50");
        ClassSlot b = ClassSlot.of("CSE 3207", DayOfWeek.MONDAY, "10:00", "10:50");
        assertFalse(registration.hasTimeClash(a, b));
    }

    // ---------- Equivalence partitioning: registration window 5-15 Jan 2027 (Section 2.4) ----------

    @Test
    void b08_dateBeforeWindowIsRejected() {
        assertEquals("Registration Not Open Yet", registration.checkRegistrationDate(LocalDate.of(2027, 1, 1)));
    }

    @Test
    void b09_dateInsideWindowIsAccepted() {
        assertEquals("Registration Open", registration.checkRegistrationDate(LocalDate.of(2027, 1, 10)));
    }

    @Test
    void b10_dateAfterWindowIsRejected() {
        assertEquals("Registration Closed", registration.checkRegistrationDate(LocalDate.of(2027, 1, 20)));
    }
}
