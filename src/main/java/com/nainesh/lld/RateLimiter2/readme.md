# 🧩 Rate Limiter - LLD

## 🧠 Assumptions

* Each request has a unique id (userId / API key / IP)
* Time is tracked in seconds
* Each client has its own rate limit (e.g., 100 requests / 60 seconds)

---

## 🔌 API

- boolean allowRequest(String clientId);
- void configureLimit(String clientId, int maxRequests, int windowInSeconds);

## Optional APIs (Bonus)
- int getRemainingRequests(String clientId);
- void resetLimit(String clientId);
---

## 🎯 Focus Areas

* Basic validations:
    * Client must have a configured limit
    * Do not exceed allowed requests
    * Correct handling of time window
    * No partial allow/deny
* Handle **concurrent requests** (basic thread safety)

- Sliding window
- Token bucket
- Thread safety
