# Git Workflow

## Branch Strategy
- `main`: always stable, presentation-ready
- `develop`: integration branch
- feature branches for implementation
- docs branches for diagrams and report assets

## Branch Naming
- `feature/phase-1-foundation`
- `feature/ui-thymeleaf-shell`
- `feature/auth-session`
- `feature/assignment-management`
- `feature/upload-validation`
- `feature/plagiarism`
- `feature/batch-engine`
- `feature/java-core-grading`
- `feature/oop-analysis`
- `feature/results-export`
- `test/auth-and-controller-tests`
- `docs/phase-2-diagrams`
- `docs/final-report-assets`

## Recommended Owner Per Branch

| Branch | Owner | Purpose |
|---|---|---|
| `feature/phase-1-foundation` | Member A | build cleanup, configs, package skeleton |
| `feature/ui-thymeleaf-shell` | Member B | base layout and Thymeleaf fragments |
| `test/auth-and-controller-tests` | Member C | auth and controller test suite |
| `docs/phase-2-diagrams` | Member D | use case, ERD, activity, class diagrams |
| `feature/auth-session` | Member A | login and Spring Security |
| `feature/assignment-management` | Member A | semester/assignment/problem/testcase flow |
| `feature/upload-validation` | Member A | upload and validation pipeline |
| `feature/plagiarism` | Member A | plagiarism engine |
| `feature/batch-engine` | Member A | async processing and polling |
| `feature/java-core-grading` | Member A | compile/run/score |
| `feature/oop-analysis` | Member A | OOP rule checks |
| `feature/results-export` | Member A/B | result UI and export integration |

## Daily Flow
1. Pull latest `develop`
2. Create or update your branch
3. Make a small set of related changes
4. Run checks locally
5. Commit with clear message
6. Open PR into `develop`
7. Resolve review comments
8. Merge after review

## Commit Message Convention
- `feat: add login form and session config`
- `fix: reject zip entries outside sandbox`
- `test: add upload validation integration tests`
- `docs: add ERD and class diagram`
- `refactor: extract output normalization strategy`
- `style: polish thymeleaf fragments`

## Pull Request Template
- Summary
- Scope
- Files changed
- How to test
- Screenshots if UI changed
- Risks

## Merge Rules
- Do not commit directly to `main`
- Only merge into `main` from `develop`
- `main` merge points:
  - end of foundation
  - end of auth + assignment flow
  - end of Java Core grading
  - final demo freeze

## Conflict Rules
- Member A resolves architectural conflicts
- Member B resolves template/style conflicts
- Member C resolves test profile conflicts
- Member D resolves docs/diagram conflicts

## Honest Contribution Rule
- Commit only the work you actually performed.
- Use `Co-authored-by` only for real pair work.
- Do not rewrite authorship to simulate participation.
