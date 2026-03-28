# Repository Setup Plan

## Repository Name
- Suggested name: `se2-java-grader`

## Initial Branch Setup
```bash
git init
git branch -M main
git checkout -b develop
```

## Initial Branch Creation
```bash
git checkout -b feature/phase-1-foundation
git checkout develop
git checkout -b feature/ui-thymeleaf-shell
git checkout develop
git checkout -b test/auth-and-controller-tests
git checkout develop
git checkout -b docs/phase-2-diagrams
git checkout develop
```

## Directory Setup
```text
docs/
docs/diagrams/
docs/report-assets/
sample-data/
scripts/
```

## Repo Milestones

### Milestone 1
- foundation stable
- test profile works
- Thymeleaf shell exists

### Milestone 2
- auth works
- assignment management works
- diagrams updated

### Milestone 3
- upload + validation + plagiarism work
- demo dataset prepared

### Milestone 4
- Java Core grading works end-to-end
- results and CSV export work

### Milestone 5
- OOP analysis added
- hardening and report completed

## Labels For Issues
- `phase-0`
- `phase-1`
- `phase-2`
- `backend`
- `ui`
- `test`
- `docs`
- `qa`
- `bug`
- `report`

## First Issues To Create
- `Phase 1: make project build and tests pass`
- `Phase 1: create Thymeleaf base layout`
- `Phase 1: add test profile and smoke tests`
- `Phase 2: create ERD and class diagram`

## First Real Ownership Split

| Issue | Owner |
|---|---|
| Fix Maven/test baseline | Member A |
| Create Thymeleaf layout shell | Member B |
| Add H2 test profile and smoke tests | Member C |
| Write setup guide and diagram placeholders | Member D |

## Definition of Ready Before Coding
- Phase 0 freeze approved
- branch naming agreed
- owner of each first issue assigned
- local environment setup shared with whole team

## Definition of Done For Each Issue
- code/docs pushed to branch
- local verification done
- PR opened
- reviewer assigned
- acceptance criteria checked
