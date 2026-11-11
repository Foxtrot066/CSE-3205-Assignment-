package suams.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Unit tests (U) and boundary value tests (B) for getLetterGrade(). */
class GradeCalculatorTest {

    // ---------- Unit tests (Section 2.1) ----------

    @Test
    void u04_73MarksGivesAMinus() {
        assertEquals("A-", GradeCalculator.getLetterGrade(73));
    }

    @Test
    void u05_58MarksGivesBMinus() {
        assertEquals("B-", GradeCalculator.getLetterGrade(58));
    }

    @Test
    void u06_marksAbove100AreRejected() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.getLetterGrade(105));
    }

    // ---------- Boundary value analysis (Section 2.4) ----------

    @Test
    void b01_justBelowZeroIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.getLetterGrade(-1));
    }

    @Test
    void b02_zeroIsF() {
        assertEquals("F", GradeCalculator.getLetterGrade(0));
    }

    @Test
    void b03_justBelowPassMarkIsF() {
        assertEquals("F", GradeCalculator.getLetterGrade(39));
    }

    @Test
    void b04_passMarkIsD() {
        assertEquals("D", GradeCalculator.getLetterGrade(40));
    }

    @Test
    void b05_justAbovePassMarkIsD() {
        assertEquals("D", GradeCalculator.getLetterGrade(41));
    }

    @Test
    void b06_hundredIsAPlus() {
        assertEquals("A+", GradeCalculator.getLetterGrade(100));
    }

    @Test
    void b07_justAboveHundredIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.getLetterGrade(101));
    }
}
