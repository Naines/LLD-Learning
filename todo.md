# TODO (Pending / Incomplete Implementations)

This file tracks areas in the repo where implementation is missing or intentionally left as a stub.

## 1) LLD modules with *only* a `readme.md` (code not started yet)

- `src/main/java/com/nainesh/lld/browserhistory/readme.md`
  - Browser History design (visit/back/forward, multi-tab, persistence).

- `src/main/java/com/nainesh/lld/ChessGame/readme.md`
  - Chess engine design (piece movement, rules, board management).

- `src/main/java/com/nainesh/lld/cricbuzz/readme.md`
  - Cricbuzz/live-score system (match creation, ball-by-ball score updates, live matches).

- `src/main/java/com/nainesh/lld/Elevator/readme.md`
  - Elevator system (states, dispatch strategy, scheduling, door operations).

- `src/main/java/com/nainesh/lld/FileSystem/readme.md`
  - File-system style tree structure (Composite pattern).

- `src/main/java/com/nainesh/lld/NotificationSystem/readme.md`
  - Notification system (multiple channels, strategy pattern).

- `src/main/java/com/nainesh/lld/paymentGateway/readme.md`
  - Payment gateway (multiple payment methods, extensibility).

- `src/main/java/com/nainesh/lld/Snabbit2/readme.md`
  - Snabbit-like home services (request lifecycle + partner assignment).

- `src/main/java/com/nainesh/lld/SnakeAndLadder/readme.md`
  - Snake and Ladder game engine (turn management).

- `src/main/java/com/nainesh/lld/Splitwise/readme.md`
  - Splitwise (expense sharing, balances, simplification logic).

- `src/main/java/com/nainesh/lld/TaskManagementSystem/readme.md`
  - Task Management system (create/complete tasks, deadline validation).

- `src/main/java/com/nainesh/lld/TicTacToe/readme.md`
  - TicTacToe (board representation, winner detection, edge cases).

## 2) Concurrency / notes-only directories

These appear to be notes / prompts and currently have no Java implementation.

- `src/main/java/com/nainesh/java/Challenges/readme.md`
  - List of concurrency problems to implement (thread pool, scheduler, web crawler, etc.).

- `src/main/java/com/nainesh/java/concurrency/lldConcepts/coordination/readme.md`
  - Coordination notes (queue, producer/consumer concepts).

- `src/main/java/com/nainesh/java/concurrency/lldConcepts/scarcity/readme.md`
  - Scarcity notes (check-then-act, read-then-modify).

## 3) Partially implemented / stubbed code

- `src/main/java/com/nainesh/java/concurrency/ThreadPool/CustomThreadPool.java`
  - Contains a commented/unfinished stub for a custom rejection policy (`RejectionPOlicy`).

- `src/main/java/com/nainesh/lld/ThreadPool/MyThreadPool.java`
  - `src/main/java/com/nainesh/lld/ThreadPool/readme.md` lists APIs not implemented yet:
    - `List<Runnable> shutdownNow()`
    - `int getActiveThreadCount()`
    - Rejection handling
    - Bounded task-queue capacity (constructor has a `size` parameter, but queue is currently unbounded)
