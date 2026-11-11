# SUAMS – Course Registration and Examination Result Module

CSE 3205 (Software Engineering) assignment project for the **Smart University Academic Management System (SUAMS)**.

| Package | What it contains |
|---|---|
| `suams.registration` | `CourseRegistration` (time-clash check, registration window 5–15 Jan 2027, register/drop) and `ExamFormService` |
| `suams.registration.chain` | Chain of Responsibility for registration rules: `RegistrationService` (client), `RegistrationHandler` (abstract handler), `WindowHandler`, `TimeClashHandler`, `CreditLimitHandler`, `DuesHandler`, `SeatHandler`, and `RegistrationRequest` |
| `suams.result` | `GradeCalculator.getLetterGrade()`, `ResultStatusService.getResultStatus()` (basis-path tested, V(G) = 4), `ResultSheet` and `ResultPublisher` |
| `suams.notification` | `NotificationService` interface (replaced by stubs in the integration tests) |

## Run the tests

```bash
mvn -B test
```

JUnit 5 runs 32 automated tests: unit (U), integration (I), black-box (B), basis-path (W) and loop (L) tests.

## Workflow

- `main` is the protected production branch.
- Work is done on `feature/<name>` branches.
- Changes reach `main` only through a reviewed Pull Request whose JUnit check has passed.
- Releases are tagged, for example `v1.0.0`.
