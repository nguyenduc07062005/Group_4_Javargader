# Data Dictionary

## Teacher
| Field | Type | Required | Notes |
|---|---|---|---|
| `teacher_id` | bigint | yes | Primary key |
| `username` | string | yes | Login identifier |
| `email` | string | yes | Unique email |
| `password_hash` | string | yes | BCrypt hash |
| `full_name` | string | yes | Display name |
| `active` | boolean | yes | Soft enable flag |

## Semester
| Field | Type | Required | Notes |
|---|---|---|---|
| `semester_id` | bigint | yes | Primary key |
| `semester_code` | string | yes | Unique code, example `SPRING2026` |
| `display_name` | string | yes | Example `Spring 2026` |
| `created_by` | bigint | yes | FK to teacher |
| `created_at` | datetime | yes | Audit timestamp |

## Assignment
| Field | Type | Required | Notes |
|---|---|---|---|
| `assignment_id` | bigint | yes | Primary key |
| `semester_id` | bigint | yes | FK to semester |
| `assignment_name` | string | yes | Teacher-visible title |
| `grading_mode` | enum | yes | `JAVA_CORE` or `OOP` |
| `normalization_policy` | enum | yes | Output comparison strategy |
| `plagiarism_threshold` | int | yes | Range `0..100` |
| `logic_weight` | decimal | no | Used for OOP mode |
| `oop_weight` | decimal | no | Used for OOP mode |
| `description_file_name` | string | no | Original uploaded file name |
| `description_storage_path` | string | no | Server storage path |
| `created_at` | datetime | yes | Audit timestamp |

## Problem
| Field | Type | Required | Notes |
|---|---|---|---|
| `problem_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `display_order` | int | yes | Preserves `bai{index}` mapping |
| `problem_name` | string | yes | Example `Problem 1` |
| `problem_description` | text | yes | Problem statement |
| `entrypoint_class` | string | yes | Default `Main` |

## TestCase
| Field | Type | Required | Notes |
|---|---|---|---|
| `test_case_id` | bigint | yes | Primary key |
| `problem_id` | bigint | yes | FK to problem |
| `external_case_id` | string | yes | Imported ID, example `01` |
| `input_data` | text | yes | Preserves newlines |
| `expected_output` | text | yes | Preserves newlines |
| `weight` | decimal | yes | Positive value |
| `display_order` | int | yes | Stable UI order |

## OopRuleConfig
| Field | Type | Required | Notes |
|---|---|---|---|
| `rule_config_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `source_type` | enum | yes | `DEFAULT` or `UPLOADED` |
| `storage_path` | string | no | JSON/YAML file location |
| `max_oop_deduction` | decimal | yes | Deduction cap |
| `active` | boolean | yes | Exactly one active config per assignment |

## Submission
| Field | Type | Required | Notes |
|---|---|---|---|
| `submission_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `student_id` | string | yes | Extracted from package naming |
| `original_zip_name` | string | yes | Uploaded file name |
| `status` | enum | yes | Upload, validation, plagiarism, and grading lifecycle |
| `uploaded_at` | datetime | yes | Upload timestamp |
| `validated_at` | datetime | no | Validation completion |
| `graded_at` | datetime | no | Final grading completion |
| `terminal_error_code` | string | no | Validation or system failure code |

## SubmissionArtifact
| Field | Type | Required | Notes |
|---|---|---|---|
| `artifact_id` | bigint | yes | Primary key |
| `submission_id` | bigint | yes | FK to submission |
| `zip_storage_path` | string | yes | Stored ZIP location |
| `extract_root_path` | string | no | Sandbox extraction path |
| `compile_log_path` | string | no | Aggregate compile log location |
| `runtime_log_path` | string | no | Aggregate runtime log location |

## ValidationIssue
| Field | Type | Required | Notes |
|---|---|---|---|
| `validation_issue_id` | bigint | yes | Primary key |
| `submission_id` | bigint | yes | FK to submission |
| `issue_code` | string | yes | Example `VAL_PACKAGE_WRONG` |
| `issue_message` | string | yes | Teacher-visible explanation |
| `file_name` | string | no | Related file if available |
| `severity` | enum | yes | Usually `ERROR` |

## PlagiarismRun
| Field | Type | Required | Notes |
|---|---|---|---|
| `plagiarism_run_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `started_by` | bigint | yes | FK to teacher |
| `threshold_used` | int | yes | Snapshot of current threshold |
| `started_at` | datetime | yes | Start timestamp |
| `finished_at` | datetime | no | End timestamp |

## PlagiarismPair
| Field | Type | Required | Notes |
|---|---|---|---|
| `plagiarism_pair_id` | bigint | yes | Primary key |
| `plagiarism_run_id` | bigint | yes | FK to run |
| `left_submission_id` | bigint | yes | FK to submission |
| `right_submission_id` | bigint | yes | FK to submission |
| `similarity_score` | int | yes | Range `0..100` |
| `blocked` | boolean | yes | Whether threshold was met |

## Batch
| Field | Type | Required | Notes |
|---|---|---|---|
| `batch_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `started_by` | bigint | yes | FK to teacher |
| `batch_status` | enum | yes | `RUNNING`, `COMPLETED`, `FAILED` |
| `started_at` | datetime | yes | Start timestamp |
| `finished_at` | datetime | no | End timestamp |

## BatchItem
| Field | Type | Required | Notes |
|---|---|---|---|
| `batch_item_id` | bigint | yes | Primary key |
| `batch_id` | bigint | yes | FK to batch |
| `submission_id` | bigint | yes | FK to submission |
| `queue_status` | enum | yes | `QUEUED`, `PROCESSING`, terminal status |
| `queued_at` | datetime | yes | Queue timestamp |
| `started_at` | datetime | no | Processing start |
| `finished_at` | datetime | no | Processing end |

## ProblemResult
| Field | Type | Required | Notes |
|---|---|---|---|
| `problem_result_id` | bigint | yes | Primary key |
| `submission_id` | bigint | yes | FK to submission |
| `problem_id` | bigint | yes | FK to problem |
| `logic_score` | decimal | no | Used in both modes |
| `oop_score` | decimal | no | OOP mode only |
| `final_problem_score` | decimal | yes | Persisted score |
| `compile_status` | enum | yes | `COMPILED`, `FAILED_COMPILE` |

## TestCaseResult
| Field | Type | Required | Notes |
|---|---|---|---|
| `test_case_result_id` | bigint | yes | Primary key |
| `problem_result_id` | bigint | yes | FK to problem result |
| `test_case_id` | bigint | yes | FK to test case |
| `result_status` | enum | yes | `PASS`, `FAIL`, `TIMEOUT`, `RUNTIME_ERROR`, `COMPILE_ERROR` |
| `actual_output` | text | no | Truncated if needed |
| `execution_time_ms` | bigint | no | Runtime in milliseconds |
| `points_earned` | decimal | yes | Earned points for this case |
| `reason_text` | string | yes | Human-readable explanation |

## OopViolation
| Field | Type | Required | Notes |
|---|---|---|---|
| `oop_violation_id` | bigint | yes | Primary key |
| `submission_id` | bigint | yes | FK to submission |
| `problem_id` | bigint | yes | FK to problem |
| `rule_id` | string | yes | Example `RULE-ENC-01` |
| `file_name` | string | yes | Source file name |
| `class_name` | string | yes | Target class |
| `line_number` | int | no | Parser-derived line number |
| `severity` | enum | yes | Rule severity |
| `deduction_points` | decimal | yes | Applied deduction |
| `violation_message` | string | yes | Teacher-visible message |

## ReportExport
| Field | Type | Required | Notes |
|---|---|---|---|
| `report_export_id` | bigint | yes | Primary key |
| `assignment_id` | bigint | yes | FK to assignment |
| `batch_id` | bigint | no | Optional batch scope |
| `requested_by` | bigint | yes | FK to teacher |
| `export_type` | enum | yes | `CSV_GRADEBOOK`, `CSV_DETAIL`, `PDF_SUMMARY` |
| `storage_path` | string | yes | Generated file path |
| `generated_at` | datetime | yes | Creation timestamp |

## AuditLog
| Field | Type | Required | Notes |
|---|---|---|---|
| `audit_log_id` | bigint | yes | Primary key |
| `teacher_id` | bigint | yes | FK to teacher |
| `action_type` | string | yes | Example `PLAGIARISM_OVERRIDE` |
| `entity_type` | string | yes | Example `SUBMISSION` |
| `entity_id` | bigint | yes | Target entity ID |
| `detail_json` | text | no | Serialized detail snapshot |
| `created_at` | datetime | yes | Audit timestamp |
