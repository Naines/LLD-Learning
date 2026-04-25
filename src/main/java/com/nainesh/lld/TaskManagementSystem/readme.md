## Task Management System
Assumptions:
- Users create tasks with deadlines.
- Tasks can be completed.

APIs:
- String createTask(String userId, String description, LocalDate deadline)
- boolean completeTask(String taskId)

Focus Areas:
- Validate deadlines.
- Maintain task lists.
- Prevent duplicate IDs.
