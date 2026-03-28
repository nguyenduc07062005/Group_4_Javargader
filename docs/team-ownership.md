# Team Ownership Plan

## Team Size
- Total members: `4`
- Working model:
  - `Member A`: coding lead / architecture lead
  - `Member B`: Thymeleaf UI owner
  - `Member C`: test and security verification owner
  - `Member D`: QA, sample data, docs, and validation owner

## Core Rule
- Code written by a person should be committed by that person.
- Do not fabricate commit history.
- If `Member A` writes most of the implementation, the other members must own real deliverables:
  - tests
  - UI templates
  - sample datasets
  - bug verification
  - diagrams
  - setup/demo docs

## Recommended Member Mapping

| Member | Role | Main responsibility |
|---|---|---|
| Member A | Coding Lead | architecture, service layer, grading engine, integration |
| Member B | UI Owner | Thymeleaf layouts, fragments, pages, CSS cleanup |
| Member C | Test Owner | MockMvc tests, repository tests, auth tests, regression checks |
| Member D | QA/Docs Owner | sample submissions, validation datasets, diagrams, setup/demo docs |

## Phase Ownership

| Phase | Primary Owner | Support Owner | Real output |
|---|---|---|---|
| Phase 0 - Requirement Freeze | Member A | Member D | scope freeze, conflict table, report baseline |
| Phase 1 - Foundation | Member A | Member C | runnable project, config profiles, package skeleton, baseline tests |
| Phase 2 - Architecture Artifacts | Member A | Member D | ERD, class diagram, activity/use case, traceability |
| Phase 3 - UI Skeleton | Member B | Member A | layout, fragments, page shells, shared components |
| Phase 4 - Auth + Session | Member A | Member C | security config, login flow, auth tests |
| Phase 5 - Assignment Management | Member A | Member B/C | CRUD screens, testcase preview, validation tests |
| Phase 6 - Upload + Validation | Member A | Member D/C | upload flow, invalid dataset pack, validation tests |
| Phase 7 - Plagiarism | Member A | Member D/C | similarity dataset, threshold tests, plagiarism result view support |
| Phase 8 - Batch Engine | Member A | Member C | worker pool, status polling tests, lifecycle verification |
| Phase 9 - Java Core Grading | Member A | Member C/D | grading logic, score tests, demo submissions |
| Phase 10 - OOP Analysis | Member A | Member C/D | rule tests, OOP sample assignments, violation verification |
| Phase 11 - Results + Export | Member A/B | Member C/D | result pages, export tests, demo output files |
| Phase 12 - Hardening + Demo | Member A | All | final QA, screenshots, demo script, report evidence |

## File Ownership Guide

### Member A
- `src/main/java/**/config/`
- `src/main/java/**/service/`
- `src/main/java/**/grading/`
- `src/main/java/**/storage/`
- integration decisions across modules

### Member B
- `src/main/resources/templates/`
- `src/main/resources/static/css/`
- `src/main/resources/static/js/` for small progressive enhancement only
- UI consistency and form rendering

### Member C
- `src/test/java/`
- test profiles and test config
- controller, service, repository, security tests

### Member D
- `sample-data/`
- `docs/`
- `docs/diagrams/`
- QA checklists and demo assets

## Commit Expectations Per Member

### Member A
- `feat: add assignment service and controller`
- `feat: implement grading pipeline`
- `refactor: extract plagiarism strategy interface`

### Member B
- `feat: add thymeleaf base layout and sidebar`
- `feat: add assignment detail template`
- `style: clean dashboard table and status badges`

### Member C
- `test: add auth controller tests`
- `test: add grading score calculation tests`
- `fix: correct failing repository test setup`

### Member D
- `docs: add use case and activity diagrams`
- `docs: add setup and demo guide`
- `test: add invalid zip and plagiarism sample datasets`

## Review Rules
- Every PR must have at least one reviewer.
- UI PRs: reviewed by Member A.
- Core backend PRs: reviewed by Member C for testability.
- Test PRs: reviewed by Member A.
- Docs and diagram PRs: reviewed by Member A or D.

## What To Tell The Instructor If Asked
- Member A acts as coding lead to keep architecture consistent.
- Member B owns server-rendered UI with Thymeleaf.
- Member C owns testing and verification.
- Member D owns QA datasets, diagrams, and technical documentation.
- Contributions are specialized, not artificially equal.
