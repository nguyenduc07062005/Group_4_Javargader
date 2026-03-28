# Use Case Diagram

## Purpose
This diagram maps the single actor `Teacher` to the seven approved use cases in the SRS.

```mermaid
flowchart LR
    Teacher["Teacher"]

    UC01(("UC-01 Login / Logout"))
    UC02(("UC-02 Define Grading Criteria"))
    UC03(("UC-03 Upload Submissions"))
    UC04(("UC-04 Run Plagiarism Check"))
    UC05(("UC-05 Start Batch Grading"))
    UC06(("UC-06 View Results and Logs"))
    UC07(("UC-07 Download Reports"))

    Teacher --> UC01
    Teacher --> UC02
    Teacher --> UC03
    Teacher --> UC04
    Teacher --> UC05
    Teacher --> UC06
    Teacher --> UC07

    UC03 -. auto validation .-> UC04
    UC04 -. precondition .-> UC05
    UC05 -. produces result data .-> UC06
    UC06 -. exports .-> UC07
```

## Notes
- `UC-01` covers `FR-01..03`.
- `UC-02` covers semester, assignment, problem, test case, and OOP rule setup.
- `UC-03` includes automatic validation after upload.
- `UC-04` must run before `UC-05`.
- `UC-06` is read-only result inspection.
- `UC-07` generates CSV and PDF outputs from persisted results.
