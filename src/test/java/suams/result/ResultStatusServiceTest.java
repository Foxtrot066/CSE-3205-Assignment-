package suams.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Basis-path tests (W) for getResultStatus(): V(G) = 4, so four independent paths. */
class ResultStatusServiceTest {

    private final ResultStatusService service = new ResultStatusService();

    @Test
    void w01_path_1_2_8_notPublished() {
        assertEquals("Result Not Published", service.getResultStatus(false, true, 65));
    }

    @Test
    void w02_path_1_3_4_8_withheldForDues() {
        assertEquals("Result Withheld", service.getResultStatus(true, false, 65));
    }

    @Test
    void w03_path_1_3_5_6_8_passed() {
        assertEquals("Passed", service.getResultStatus(true, true, 65));
    }

    @Test
    void w04_path_1_3_5_7_8_failed() {
        assertEquals("Failed", service.getResultStatus(true, true, 32));
    }
}
