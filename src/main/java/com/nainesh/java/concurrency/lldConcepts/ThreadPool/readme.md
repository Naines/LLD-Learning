Idea:
Create few threads -> reuse them -> submit tasks repeatedly


submit to TPExecutor , which assigns to some worker thread, this goes to task queue, if thread unavailable.

ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime(ttl), unit (eg. seconds, ms) , workQueue (DS))
core pool size-> min worker thread kept alive.
max pool size -> if queue full, create extra threads, until maxPoolSize
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
