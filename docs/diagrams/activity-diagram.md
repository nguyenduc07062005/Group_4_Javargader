# Activity Diagram

## Main Teacher Workflow

```mermaid
flowchart TD
    Start([Start])
    Login["Teacher opens login page and submits credentials"]
    Auth{"Credentials valid?"}
    Dashboard["Dashboard"]
    CreateAssign["Create semester and assignment"]
    DefineProblems["Define problems, import test cases, save settings"]
    Upload["Upload submission ZIP files"]
    AutoValidate["System runs upload validation pipeline"]
    Valid{"Validation passed?"}
    Reject["Mark submission REJECTED with error code"]
    RunPlag["Run plagiarism check"]
    Blocked{"Flagged above threshold?"}
    Override{"Teacher overrides false positive?"}
    Hold["Keep PLAGIARISM_BLOCKED"]
    Queue["Create batch and enqueue VALIDATED submissions"]
    Worker["Bounded worker pool processes queue"]
    Mode{"Assignment mode?"}
    Core["Compile, execute test cases, score JAVA_CORE"]
    Oop["Compile, execute, run OOP analysis, compute weighted score"]
    Persist["Persist total score, per-problem score, testcase results, logs, violations"]
    View["View batch progress and submission detail report"]
    Export["Download CSV Gradebook / CSV Detail / PDF Summary"]
    End([End])

    Start --> Login --> Auth
    Auth -- No --> Login
    Auth -- Yes --> Dashboard --> CreateAssign --> DefineProblems --> Upload --> AutoValidate --> Valid
    Valid -- No --> Reject --> View
    Valid -- Yes --> RunPlag --> Blocked
    Blocked -- Yes --> Override
    Override -- No --> Hold --> View
    Override -- Yes --> Queue
    Blocked -- No --> Queue
    Queue --> Worker --> Mode
    Mode -- JAVA_CORE --> Core --> Persist
    Mode -- OOP --> Oop --> Persist
    Persist --> View --> Export --> End
```

## Activity checkpoints
- Upload immediately triggers validation.
- Plagiarism is a gate before grading.
- Batch grading is asynchronous.
- Both grading modes converge at the same persistence and reporting stage.
