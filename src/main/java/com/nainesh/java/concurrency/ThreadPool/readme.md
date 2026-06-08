Idea:
Create few threads -> reuse them -> submit tasks repeatedly


submit to TPExecutor , which assigns to some worker thread, this goes to task queue, if thread unavailable.

ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime(ttl), unit (eg. seconds, ms) , workQueue (DS))


Core pool size -> Minimum worker threads kept alive.
Queue -> Stores waiting tasks.
Max pool size -> Maximum threads allowed. if queue full, create extra threads, until maxPoolSize
Rejected tasks -> If queue full + max threads reached.
 
keep alive time -> extra thread beyond core.
 { idle -> wait keepALiveTime -> destroy }


Rejection Policies:
(when queue full
AND
max threads reached)

1. AbortPolicy - throw Exception
2. CallerRunsPolicy - caller thread executes task
3. Discard Policy - drop task
4. Dicard Oldest task - from queue

Executors.newFixedTP(5) , core=5, max=5,unbounded queue
CachedTp , core=0, max=huge, creates threads aggresively
SingleTp, 1 worker


TP improve performance as:
Avoid thread creation cost
Reuse workers
Reduce context switching


# Walkthrough
A MyThreadPoolExecutor instance is created, with given
CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME and QUEUE_SIZE.
Then, a NUMBER_OF_TASKS are passed to the executor, one by one. If the executor refuses task, then the task is passed to the executor, until the executor accepts it.

Given the time when tasks are passed to the executor, the threadPool gains in size. When it reaches the CORE_POOL_SIZE number of threads, the new tasks are put in the task queue. If the task queue is full, any new task forces a creation of a new thread, until the MAXIMUM_POOL_SIZE number of threads is reached. At that point, MyThreadPoolExecutor will reject the task.

The internals of MyThread are pretty self explanatory. While the thread pool executor is active, the thread will keep on getting new tasks. The thread starts with the task given to it at the initialization. After it finished that task, it tries to get and run tasks from the task queue. If the thread does not have any tasks to run, it will wait for KEEP_ALIVE_TIME milliseconds (extended thread) or indefinitely (core thread), until the thread pool executor notifies it that a new task is in the queue. If the waiting time expires or a notify signal is issued by the executor, the thread checks again if there are any tasks left in the task queue. If the task queue is empty, the thread is removed from the thread pool.

When there are no more threads in the thread pool, a destroy signal is sent to the thread pool executor and the application finishes.
