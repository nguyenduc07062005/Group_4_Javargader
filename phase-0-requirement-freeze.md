# Phase 0 - Requirement Freeze

## Objective
Freeze the implementation baseline before feature development so the team does not build against conflicting assumptions.

## Source Priority
1. `Group4_SRS_Phase3_JavaCodeGraderfinal.docx`
2. Course lecture constraints
3. `gợi ý của thầy.pdf`
4. Current repository state

## Why This Freeze Is Needed
The repo is still near-empty, while the requirement sources are not fully aligned. If coding starts before freezing these decisions, the team is likely to:
- build the wrong architecture
- implement the wrong package convention
- waste time on non-required export/report formats
- create report artifacts that do not match the actual code

## Frozen Product Definition
- Product type: `server-side web application`
- Frontend style: `Thymeleaf-only`
- Architectural style: `MVC + layered architecture`
- Authentication: `Spring Security session-based`, not JWT
- Database style: `relational database`
- Execution model: `server-side grading with isolated workspace + timeout`
- Primary language in this semester: `Java only`

## Requirement Conflict Resolution

| Topic | SRS | Guidance PDF | Frozen Decision | Rationale |
|---|---|---|---|---|
| Product form | Server-side web app | One answer suggests local tool | Follow `server-side web app` | SRS is the formal baseline and course scope clearly includes UI pages and web flow |
| UI technology | Thymeleaf SSR | Pipeline/tool-focused, not UI-specific | `Thymeleaf-only` | Matches SRS and lectures; lowers frontend complexity |
| Auth model | Spring Security session | Not explicitly conflicting | `Spring Security + session` | Required by SRS and lecture constraints |
| Package convention | `fit_msv` | Raw MSSV package/folder appears in examples | Follow `fit_{studentId}` | SRS is explicit and also gives a matching appendix example |
| Export formats | CSV Gradebook, CSV Detail, PDF Summary | Wants detailed XLSX | CSV required, PDF optional, XLSX out of current scope | Reduces delivery risk and stays aligned to SRS |
| Plagiarism basis | AST fingerprint + deterministic similarity | Suggests text/token/AST hybrid | Use `AST fingerprint` for official blocking; optional text/token only as future extension | SRS defines AST-based decision logic |
| OOP rule config | JSON or YAML | JSON/YAML/XML | `JSON first`, parser abstraction kept open for YAML later | JSON is enough for MVP and easiest to stabilize |
| Multi-language future | Not in current scope | Mentions PHP/JS/Python future | Out of current semester scope | Avoids architecture bloat now |
| Sandbox mechanism | Isolated workspace + timeout | Suggests Docker or process limits | Process isolation + per-submission workspace + timeout for current release | Enough for course scope without operational complexity |

## Frozen Scope by Use Case

### Must complete for a stable working website
- `UC-01`: Teacher Login / Logout
- `UC-02`: Define Grading Criteria
- `UC-03`: Upload Submissions
- `UC-04`: Run Plagiarism Check
- `UC-05`: Start Batch Grading
- `UC-06`: View Results and Logs
- `UC-07`: Download Reports

### Delivery strategy
- The system should support both `JAVA_CORE` and `OOP` in architecture.
- The implementation sequence should prioritize `JAVA_CORE` end-to-end first.
- `OOP grading` is implemented after `JAVA_CORE` becomes stable.

## Frozen Functional Scope by FR Group

### Release A - Demo-safe baseline
- `FR-01` to `FR-18`
- `FR-19` to `FR-26`
- `FR-27` to `FR-35`
- `FR-43` to `FR-45`

### Release B - Final completeness target if schedule allows
- `FR-36` to `FR-42`
- `FR-46`

## Interpretation Notes
- `FR-24` requires plagiarism check before grading, so `UC-04` cannot be skipped in MVP.
- `FR-46` is useful but can be deferred because the SRS itself marks PDF under a lower priority section than persistence/CSV.
- `OOP grading` is important for architectural completeness, but it should not be allowed to block the `JAVA_CORE` production path.

## Frozen Non-Functional Constraints
- UI must use `Thymeleaf`
- Session-based authentication only
- No raw stack traces in UI
- ZIP upload limits and Zip-Slip protection are mandatory
- Each test case run must have timeout and output caps
- Batch grading must use bounded parallelism
- Dashboard/results must stay responsive enough for demo
- Audit logs are mandatory for upload, batch start, export, plagiarism override

## Frozen Implementation Assumptions
- Java version: `17`
- Build tool: `Maven`
- Framework stack:
  - `Spring Boot`
  - `Spring MVC`
  - `Thymeleaf`
  - `Spring Security`
  - `Spring Data JPA`
  - `JavaParser`
- Runtime DB decision for implementation:
  - `MySQL` for development/runtime
  - `H2` for tests if needed to stabilize CI/test execution quickly
- Schema migration tool:
  - `Flyway`

## Frozen Architecture Direction

### Web side
- Controller returns Thymeleaf views
- Form binding uses DTO/form objects
- Validation uses Bean Validation + `BindingResult`
- Shared fragments are reused for layout, messages, tables, status badges

### Domain/application side
- Service layer owns business use cases
- Repository layer owns persistence only
- No repository call directly from Thymeleaf or controller business branches
- File storage and grading execution stay in infrastructure adapters

### Grading engine side
- Modular pipeline is allowed and recommended
- But it lives behind the web app, not as a separate product
- Official processing order:
  1. assignment setup
  2. submission upload
  3. extraction + validation
  4. plagiarism check
  5. batch grading
  6. result persistence
  7. export

## Pattern Freeze for Report and Implementation
- `MVC`: web request flow
- `Repository`: persistence abstraction
- `Strategy`: grading mode and comparison policies
- `Template Method`: grading pipeline skeleton
- `Factory`: OOP rule checker creation
- `Facade`: grading/export orchestration
- `Chain of Responsibility`: optional validation step chaining

## Out of Scope for Current Semester
- Student portal
- Admin backend
- JWT auth
- React/Vue frontend
- Clone assignment from previous semester
- Edit/delete assignment after submissions uploaded
- Automatic plagiarism penalty beyond blocking
- Regrade existing submission by replacing old upload
- Multi-language grading beyond Java
- XLSX export as official required deliverable

## Phase 0 Acceptance Checklist
- [x] Product type frozen as web app
- [x] UI frozen as Thymeleaf-only
- [x] Auth frozen as session-based Spring Security
- [x] Package convention frozen as `fit_{studentId}`
- [x] CSV prioritized over XLSX
- [x] AST plagiarism chosen as official blocking logic
- [x] Java Core prioritized before OOP implementation
- [x] Out-of-scope items explicitly documented

## What Phase 1 Must Start With
1. Make the Spring Boot project run and test cleanly
2. Add environment-specific datasource configs
3. Create package skeleton for MVC + service + repository + grading modules
4. Add a stable test profile before feature development

## Short Summary for Team Alignment
- Build a `Thymeleaf-only Spring Boot website`
- Follow `SRS first`
- Implement `JAVA_CORE` full flow first
- Keep `OOP` in architecture and finish it after the core path is stable
- Do not spend sprint time on `React`, `JWT`, or `XLSX`
