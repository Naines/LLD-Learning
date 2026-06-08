# Assumptions:

Pool size is fixed during initialization.
Tasks are independent.
Tasks are executed in FIFO order.
Ignore task priority.

# APIs:

void submit(Runnable task)
void shutdown()
List<Runnable> shutdownNow()
int getActiveThreadCount()

# Focus Areas:

Thread-safe task queue.
Worker thread lifecycle.
Graceful vs immediate shutdown.
Rejection handling.
