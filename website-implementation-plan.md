# Java Grader Website Implementation Plan

## Goal
Build the `Java Core & OOP Code Grading Web Application` as a working Spring Boot web system that follows `MVC + Layered Architecture + Clean Code + SOLID`, matches the approved SRS baseline, and is easy to explain in the final report.

## Baseline Decisions
- Use `Group4_SRS_Phase3_JavaCodeGraderfinal.docx` as the formal requirement baseline.
- Use `gợi ý của thầy.pdf` as implementation guidance, not as the primary source when it conflicts with the SRS.
- Keep the product as a `server-side web application` with `Spring Boot + Thymeleaf + Spring Security session + JPA`.
- Implement the grading engine as a modular pipeline inside the backend, not as a separate local-only tool.
- Track requirement drift explicitly before coding:
  - SRS says `server-side web app`; the guidance PDF suggests `local` in one answer.
  - SRS says package format `fit_msv`; the guidance PDF shows package/folder by raw student ID.
  - SRS exports `CSV + PDF`; the guidance PDF prefers detailed `XLSX`.
  - Proposed resolution: follow SRS first, then add `XLSX` as optional extension only if time allows.

## Proposed Architecture
- `presentation` layer:
  - Thymeleaf pages, fragments, form models, validation error rendering.
  - Controllers only handle HTTP, model binding, redirects, and view selection.
- `application` layer:
  - Use-case services for login, assignment setup, upload, plagiarism run, batch grading, result viewing, export.
  - Transaction boundaries and orchestration live here.
- `domain` layer:
  - Entities: `Teacher`, `Semester`, `Assignment`, `Problem`, `TestCase`, `Submission`, `Batch`, `TestCaseResult`, `Violation`, `PlagiarismFlag`, `Report`.
  - Value objects/enums: `GradingMode`, `SubmissionStatus`, `NormalizationPolicy`, `Severity`, `ErrorCode`.
- `infrastructure` layer:
  - JPA repositories.
  - File storage for uploads, extracted workspaces, logs, generated exports.
  - Java compiler/execution adapters, JavaParser adapters, CSV/PDF export adapters.

## Pattern Mapping From Lectures
- `MVC` for web UI and request flow.
- `Layered + Repository` for Controller -> Service -> Repository separation.
- `Strategy` for `CoreGradingStrategy`, `OopGradingStrategy`, `OutputComparisonStrategy`, `PlagiarismStrategy`.
- `Template Method` for the grading pipeline skeleton: validate -> compile -> execute -> score -> persist.
- `Factory` for building rule checkers and report generators from config.
- `Facade` for export/report generation and grading orchestration.
- `Chain of Responsibility` for validation pipeline steps if the team wants configurable step-by-step checking.

## Tasks
- [ ] Task 1: Freeze requirement baseline and resolve conflicts between SRS and guidance PDF.  
  Verify: one signed-off decision table exists for conflicts (`web vs local`, `fit_msv vs MSSV`, `CSV/PDF vs XLSX`).
- [ ] Task 2: Reset project foundation to course-aligned stack and runnable baseline.  
  Verify: app starts, `mvn test` passes, datasource profile strategy is defined, package structure is created.
- [ ] Task 3: Design domain model, ERD, data dictionary, and class diagram before major coding.  
  Verify: diagrams cover all SRS core entities and each FR group maps to at least one module/class.
- [ ] Task 4: Implement authentication and teacher session flow with Spring Security.  
  Verify: login, logout, session timeout, protected routes, and error handling follow `FR-01` to `FR-03`.
- [ ] Task 5: Implement semester, assignment, problem, test case, and OOP-rule configuration management.  
  Verify: teacher can create assignment with problems/test cases and persist config per `FR-04` to `FR-11`.
- [ ] Task 6: Implement submission upload, ZIP safety, extraction, and auto-validation pipeline.  
  Verify: valid ZIP becomes `VALIDATED`; invalid ZIP gets exact rejection code per `FR-12` to `FR-18`.
- [ ] Task 7: Implement plagiarism engine and manual override workflow.  
  Verify: similarity run updates blocked submissions and logs override actions per `FR-19` to `FR-23`.
- [ ] Task 8: Implement batch grading engine for `JAVA_CORE` first, then extend to `OOP`.  
  Verify: queued submissions run asynchronously, compute scores, save logs/results, and satisfy `FR-24` to `FR-42`.
- [ ] Task 9: Implement result pages, exports, tests, and demo hardening.  
  Verify: teacher can view results, export CSV/PDF, and the system satisfies main NFR/PERF targets.

## Build Order
1. `Must Have` web skeleton + login + assignment CRUD
2. upload + validation pipeline
3. plagiarism check
4. Java Core grading
5. results + export
6. OOP static analysis
7. performance, security hardening, report artifacts

## Module Breakdown
- `auth`: login, session, teacher account lookup
- `semester`: semester CRUD/filtering
- `assignment`: assignment/problem/test case/rule config
- `submission`: upload, storage, extraction, validation
- `plagiarism`: normalization, fingerprinting, comparison, override
- `grading`: queue, workers, compile, run, scoring
- `reporting`: result pages, CSV detail, CSV gradebook, PDF summary
- `shared`: exception model, error codes, config, utilities

## Quality Rules
- Controllers never access repositories directly.
- Business rules never live in Thymeleaf templates.
- Keep one reason to change per class where possible.
- Prefer constructor injection.
- Use DTO/form objects for web binding.
- Keep grading pipeline components behind interfaces.
- Treat student code execution as untrusted input.

## Testing Plan
- Unit tests:
  - score calculation
  - output normalization
  - validation rules
  - plagiarism threshold logic
  - OOP rule evaluation
- Integration tests:
  - controller flows with MockMvc
  - repository persistence with test database
  - security access rules
- System tests:
  - upload -> validate -> plagiarism -> grade -> export
- Performance/demo checks:
  - K=1 vs K=4 worker comparison
  - dashboard/result load timing

## Current Risks
- Current project fails `mvn test` because datasource is not configured.
- The project skeleton is almost empty, so schedule risk is high if coding starts before design freeze.
- Requirement drift exists between SRS and the guidance PDF.
- The grading engine is the highest technical risk because it mixes file handling, process execution, concurrency, and security.

## Report Deliverables To Prepare In Parallel
- Requirements summary and traceability matrix
- Use case diagram + written use cases
- Activity diagram for upload/grade flow
- ERD + data dictionary
- Class diagram
- Architecture diagram (`MVC + Layered + Pipeline`)
- Design pattern justification table
- Test strategy + evidence
- Risk register + progress plan

## Done When
- All `Must Have` FRs are implemented and demoable.
- Main `Should Have` items for plagiarism and OOP grading are at least partially working.
- `mvn test` passes.
- Demo flow works end to end: `login -> create assignment -> upload -> validate -> plagiarism -> batch grade -> view result -> export`.
- Diagrams and architecture explanations match the real codebase.
