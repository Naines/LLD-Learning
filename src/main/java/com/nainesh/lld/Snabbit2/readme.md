## Snabbit-like On-demand Home Services
Assumptions:
- Users create short-duration home service requests such as cleaning, chores, or repairs.
- Nearby available partners can be assigned to one active request at a time.
- Ignore payments, ratings, and route optimization for simplicity.

APIs:
- String createServiceRequest(String userId, String serviceType, Location location)
- boolean cancelServiceRequest(String requestId)
- boolean completeServiceRequest(String requestId)

Focus Areas:
- Match requests to available nearby partners.
- Prevent assigning the same partner to multiple active requests.
- Handle request cancellation, completion, and partner availability.