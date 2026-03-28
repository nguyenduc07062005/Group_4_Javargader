# Phase-by-Phase Implementation Roadmap

## Approach
Implement the system in small locked phases. Each phase has:
- a narrow goal
- concrete tasks
- explicit output artifacts
- a gate that must pass before moving on

This roadmap assumes:
- `Thymeleaf-only` UI
- `Spring Boot + Spring MVC + Spring Security + JPA`
- SRS is the primary baseline

## Scope
- In:
  - end-to-end web system for teacher grading workflow
  - architecture, coding plan, testing plan, report artifacts
- Out:
  - React/Vue/SPA frontend
  - multi-language grading beyond Java in this semester
  - advanced cloud deployment

## Phase 0 - Requirement Freeze
### Goal
Stop requirement drift before writing real code.

### Tasks
- [ ] Read and summarize the formal SRS into one decision sheet.
- [ ] Compare SRS with `gợi ý của thầy.pdf` and list conflicts.
- [ ] Confirm final decisions for:
  - `web app` vs `local tool`
  - package convention
  - export formats
  - scope of plagiarism
  - scope of OOP grading in final demo
- [ ] Mark each FR as `Must`, `Should`, or `Stretch for demo`.

### Deliverables
- Requirement baseline sheet
- Conflict resolution table
- Final MVP scope

### Gate
- Team can answer: "What exactly will be demoed?" in one page without contradiction.

## Phase 1 - Project Foundation
### Goal
Make the project build, run, and test cleanly before adding features.

### Tasks
- [ ] Normalize Maven setup and project metadata.
- [ ] Decide DB strategy:
  - local MySQL for development, or
  - H2 for tests + MySQL/PostgreSQL for runtime
- [ ] Add environment-specific config files:
  - `application.properties`
  - `application-dev.properties`
  - `application-test.properties`
- [ ] Make `mvn test` pass.
- [ ] Create base package structure:
  - `controller`
  - `service`
  - `repository`
  - `entity`
  - `dto`
  - `config`
  - `grading`
  - `storage`
  - `report`
- [ ] Add global exception handling and standard error view model.

### Deliverables
- Runnable Spring Boot app
- Passing baseline tests
- Clean package skeleton

### Gate
- App starts locally.
- `mvn test` passes.
- No feature code exists outside the intended packages.

## Phase 2 - Architecture Artifacts First
### Goal
Lock the design before writing feature-heavy code.

### Tasks
- [ ] Draw use case diagram from `UC-01..07`.
- [ ] Draw activity diagram for the main flow:
  - login -> create assignment -> upload -> validate -> plagiarism -> grade -> export
- [ ] Design ERD.
- [ ] Write data dictionary for all key entities/fields.
- [ ] Draw class diagram for major modules.
- [ ] Define package/module responsibilities.
- [ ] Define design pattern mapping for report:
  - MVC
  - Repository
  - Strategy
  - Template Method
  - Factory
  - Facade

### Deliverables
- Use case diagram
- Activity diagram
- ERD
- Data dictionary
- Class diagram
- Architecture note

### Gate
- Every major FR group maps to entities, services, repositories, and screens.

## Phase 3 - UI Skeleton and Layout
### Goal
Build the Thymeleaf foundation once, then reuse it everywhere.

### Tasks
- [ ] Create Thymeleaf layout structure:
  - `layout/base`
  - `layout/header`
  - `layout/sidebar`
  - `layout/messages`
- [ ] Create static asset folders:
  - `static/css`
  - `static/js`
- [ ] Build placeholder pages:
  - login
  - dashboard
  - assignment list/detail
  - batch page
  - report page
- [ ] Standardize page components:
  - table
  - form error block
  - status badge
  - log panel
- [ ] Add responsive baseline for width `>= 768px`.

### Deliverables
- Shared Thymeleaf fragments
- Navigation skeleton
- Reusable UI patterns

### Gate
- All target pages render with shared layout and no duplicated HTML structure.

## Phase 4 - Authentication and Session
### Goal
Finish login/logout/session security early because every other feature depends on it.

### Tasks
- [ ] Design `Teacher` entity and repository.
- [ ] Implement password hashing with BCrypt.
- [ ] Create `UserDetails` wrapper class instead of mixing security logic into entity.
- [ ] Configure `SecurityFilterChain` for form login and session-based auth.
- [ ] Protect private routes and allow only login assets publicly.
- [ ] Implement logout and session-expired handling.
- [ ] Show friendly login errors without stack traces.

### Deliverables
- Working login/logout
- Protected routes
- Session timeout behavior

### Gate
- `FR-01` to `FR-03` pass manually and in tests.

## Phase 5 - Semester and Assignment Management
### Goal
Implement the business setup flow before handling submissions.

### Tasks
- [ ] Create entities:
  - `Semester`
  - `Assignment`
  - `Problem`
  - `TestCase`
  - `OopRuleConfig`
- [ ] Build create/edit/list pages with Thymeleaf forms.
- [ ] Implement semester filtering on dashboard.
- [ ] Implement assignment creation with multiple problems.
- [ ] Implement assignment description upload and storage.
- [ ] Implement ZIP/CSV testcase import preview.
- [ ] Implement confirm-to-persist behavior after preview.
- [ ] Validate all teacher input with Bean Validation.

### Deliverables
- Assignment creation flow
- Test case import preview flow
- Persisted assignment configuration

### Gate
- `FR-04` to `FR-11` are demoable.

## Phase 6 - Submission Upload and Auto-Validation
### Goal
Build the upload path and reject bad submissions early.

### Tasks
- [ ] Create entities:
  - `Submission`
  - `SubmissionArtifact`
  - `ValidationIssue`
- [ ] Implement upload endpoint and file storage layout.
- [ ] Enforce ZIP limits and extension whitelist.
- [ ] Add Zip-Slip protection.
- [ ] Extract into isolated workspace.
- [ ] Validate:
  - package name
  - problem folder structure
  - `JAVA_CORE` Main.java rule
- [ ] Assign terminal validation status and error codes.
- [ ] Persist validation results and show them in UI.

### Deliverables
- Submission upload page
- Auto-validation pipeline
- Validation result screen

### Gate
- Invalid submissions are rejected deterministically with correct codes.

## Phase 7 - Plagiarism Engine
### Goal
Implement similarity checking as a separate bounded module.

### Tasks
- [ ] Create source normalization component.
- [ ] Create AST parsing component using JavaParser.
- [ ] Design `PlagiarismStrategy` interface.
- [ ] Implement baseline AST fingerprint strategy.
- [ ] Compute pairwise similarity for submissions in one assignment.
- [ ] Block flagged submissions above threshold.
- [ ] Add teacher override with audit log.
- [ ] Render plagiarism result table in UI.

### Deliverables
- Plagiarism run action
- Similarity result view
- Manual override flow

### Gate
- `FR-19` to `FR-23` work for a small batch dataset.

## Phase 8 - Batch Engine Infrastructure
### Goal
Create the asynchronous grading backbone before grading logic.

### Tasks
- [ ] Create entities:
  - `Batch`
  - `BatchItem`
  - `AuditLog`
- [ ] Build queueing logic for validated submissions only.
- [ ] Configure bounded worker pool.
- [ ] Implement submission status transitions.
- [ ] Add polling endpoint for live progress.
- [ ] Add UI badges and progress counts.
- [ ] Persist timestamps for benchmark and troubleshooting.

### Deliverables
- Batch creation
- Worker execution infrastructure
- Real-time status polling

### Gate
- `QUEUED -> PROCESSING -> terminal status` is stable across repeated runs.

## Phase 9 - Java Core Grading Engine
### Goal
Finish the minimum grading engine for final demo viability.

### Tasks
- [ ] Create `GradingContext`.
- [ ] Create `GradingPipeline` and step interfaces.
- [ ] Implement compile step using `javac`.
- [ ] Implement run step using isolated child process.
- [ ] Implement stdin injection from test cases.
- [ ] Implement stdout/stderr capture with size limit.
- [ ] Implement timeout kill logic.
- [ ] Implement output normalization strategy.
- [ ] Implement per-testcase reason messages.
- [ ] Compute per-problem and total score.

### Deliverables
- Working Java Core grader
- Logs for compile/runtime/timeout/output mismatch
- Persisted detailed results

### Gate
- `FR-27` to `FR-35` pass with known sample submissions.

## Phase 10 - OOP Analysis Engine
### Goal
Add the OOP extension after Java Core is stable.

### Tasks
- [ ] Implement multi-file compile support.
- [ ] Load OOP rule config from JSON/YAML.
- [ ] Build rule checker factory.
- [ ] Implement first OOP rules:
  - private fields
  - getter/setter
  - inheritance
  - @Override
- [ ] Persist violations with line number and deduction points.
- [ ] Compute OOP score and weighted final score.
- [ ] Keep parser failure non-blocking.

### Deliverables
- OOP rule engine
- Violation list in report
- Weighted OOP final scoring

### Gate
- `FR-36` to `FR-42` work for at least one OOP assignment.

## Phase 11 - Results and Export
### Goal
Finish the teacher-facing outputs.

### Tasks
- [ ] Build result detail page per submission.
- [ ] Build batch result page.
- [ ] Implement CSV Gradebook export.
- [ ] Implement CSV Detail export.
- [ ] Implement PDF Summary export.
- [ ] Add plagiarism overview to summary.
- [ ] Add download buttons and file lifecycle handling.

### Deliverables
- Result pages
- CSV exports
- PDF summary

### Gate
- `FR-43` to `FR-46` are demoable from the UI.

## Phase 12 - Testing, Hardening, and Demo Prep
### Goal
Reduce hidden defects before final review and report.

### Tasks
- [ ] Add unit tests for scoring, normalization, and rule checks.
- [ ] Add MockMvc tests for auth and main flows.
- [ ] Add repository tests for persistence integrity.
- [ ] Add integration test for upload -> validate -> grade.
- [ ] Measure `PERF-01..04`.
- [ ] Verify NFR security items:
  - session cookie
  - no stacktrace leakage
  - upload limits
  - timeout and cleanup
- [ ] Prepare sample demo dataset:
  - valid
  - compile error
  - timeout
  - plagiarism blocked
  - OOP violations
- [ ] Sync final diagrams with real code.

### Deliverables
- Test evidence
- Demo script
- Final architecture/report evidence

### Gate
- End-to-end demo works without manual patching during demo day.

## Validation
- Never start a new phase if the previous gate failed.
- After each phase:
  - run tests
  - run app manually
  - update diagrams/docs only for what is already real
- Report should be written in parallel, not at the end.

## Open Questions
- Will the final demo require `OOP grading` fully working, or is `JAVA_CORE + partial OOP analysis` acceptable?
- Do you want `XLSX` as stretch scope, or should the team strictly stop at `CSV + PDF`?
