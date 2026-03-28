# ERD

## Logical Data Model

```mermaid
erDiagram
    TEACHER {
        bigint teacher_id PK
        string username UK
        string email UK
        string password_hash
        string full_name
        boolean active
        datetime created_at
    }

    SEMESTER {
        bigint semester_id PK
        string semester_code UK
        string display_name
        datetime created_at
        bigint created_by FK
    }

    ASSIGNMENT {
        bigint assignment_id PK
        bigint semester_id FK
        string assignment_name
        string grading_mode
        string normalization_policy
        int plagiarism_threshold
        decimal logic_weight
        decimal oop_weight
        string description_file_name
        string description_storage_path
        datetime created_at
    }

    PROBLEM {
        bigint problem_id PK
        bigint assignment_id FK
        int display_order
        string problem_name
        string entrypoint_class
        text problem_description
    }

    TEST_CASE {
        bigint test_case_id PK
        bigint problem_id FK
        string external_case_id
        text input_data
        text expected_output
        decimal weight
        int display_order
    }

    OOP_RULE_CONFIG {
        bigint rule_config_id PK
        bigint assignment_id FK
        string source_type
        string storage_path
        decimal max_oop_deduction
        boolean active
    }

    SUBMISSION {
        bigint submission_id PK
        bigint assignment_id FK
        string student_id
        string original_zip_name
        string status
        datetime uploaded_at
        datetime validated_at
        datetime graded_at
        string terminal_error_code
    }

    SUBMISSION_ARTIFACT {
        bigint artifact_id PK
        bigint submission_id FK
        string zip_storage_path
        string extract_root_path
        string compile_log_path
        string runtime_log_path
    }

    VALIDATION_ISSUE {
        bigint validation_issue_id PK
        bigint submission_id FK
        string issue_code
        string issue_message
        string file_name
        string severity
    }

    PLAGIARISM_RUN {
        bigint plagiarism_run_id PK
        bigint assignment_id FK
        datetime started_at
        datetime finished_at
        int threshold_used
        bigint started_by FK
    }

    PLAGIARISM_PAIR {
        bigint plagiarism_pair_id PK
        bigint plagiarism_run_id FK
        bigint left_submission_id FK
        bigint right_submission_id FK
        int similarity_score
        boolean blocked
    }

    BATCH {
        bigint batch_id PK
        bigint assignment_id FK
        bigint started_by FK
        string batch_status
        datetime started_at
        datetime finished_at
    }

    BATCH_ITEM {
        bigint batch_item_id PK
        bigint batch_id FK
        bigint submission_id FK
        string queue_status
        datetime queued_at
        datetime started_at
        datetime finished_at
    }

    PROBLEM_RESULT {
        bigint problem_result_id PK
        bigint submission_id FK
        bigint problem_id FK
        decimal logic_score
        decimal oop_score
        decimal final_problem_score
        string compile_status
    }

    TEST_CASE_RESULT {
        bigint test_case_result_id PK
        bigint problem_result_id FK
        bigint test_case_id FK
        string result_status
        text actual_output
        bigint execution_time_ms
        decimal points_earned
        string reason_text
    }

    OOP_VIOLATION {
        bigint oop_violation_id PK
        bigint submission_id FK
        bigint problem_id FK
        string rule_id
        string file_name
        string class_name
        int line_number
        string severity
        decimal deduction_points
        string violation_message
    }

    REPORT_EXPORT {
        bigint report_export_id PK
        bigint assignment_id FK
        bigint batch_id FK
        bigint requested_by FK
        string export_type
        string storage_path
        datetime generated_at
    }

    AUDIT_LOG {
        bigint audit_log_id PK
        bigint teacher_id FK
        string action_type
        string entity_type
        bigint entity_id
        text detail_json
        datetime created_at
    }

    TEACHER ||--o{ SEMESTER : creates
    SEMESTER ||--o{ ASSIGNMENT : contains
    ASSIGNMENT ||--o{ PROBLEM : contains
    PROBLEM ||--o{ TEST_CASE : owns
    ASSIGNMENT ||--o| OOP_RULE_CONFIG : uses
    ASSIGNMENT ||--o{ SUBMISSION : receives
    SUBMISSION ||--|| SUBMISSION_ARTIFACT : stores
    SUBMISSION ||--o{ VALIDATION_ISSUE : has
    ASSIGNMENT ||--o{ PLAGIARISM_RUN : has
    PLAGIARISM_RUN ||--o{ PLAGIARISM_PAIR : compares
    ASSIGNMENT ||--o{ BATCH : has
    BATCH ||--o{ BATCH_ITEM : contains
    SUBMISSION ||--o{ BATCH_ITEM : queued_as
    SUBMISSION ||--o{ PROBLEM_RESULT : produces
    PROBLEM ||--o{ PROBLEM_RESULT : evaluated_for
    PROBLEM_RESULT ||--o{ TEST_CASE_RESULT : contains
    TEST_CASE ||--o{ TEST_CASE_RESULT : evaluated_as
    SUBMISSION ||--o{ OOP_VIOLATION : triggers
    PROBLEM ||--o{ OOP_VIOLATION : scoped_to
    ASSIGNMENT ||--o{ REPORT_EXPORT : generates
    BATCH ||--o{ REPORT_EXPORT : scopes
    TEACHER ||--o{ REPORT_EXPORT : requests
    TEACHER ||--o{ AUDIT_LOG : writes
```

## ERD notes
- `Assignment` is the aggregate root for grading configuration.
- `Submission` is the aggregate root for uploaded student work and result lifecycle.
- `ProblemResult` and `TestCaseResult` preserve detailed grading evidence for reports.
- `AuditLog` is required for teacher override and other sensitive actions.
