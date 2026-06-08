## Cricbuzz

# Assumptions:

One match can have multiple innings.
Match updates are received sequentially.
Ignore authentication/payment.
Multiple matches can run simultaneously.

# APIs:

void createMatch(Team teamA, Team teamB)
void updateScore(matchId, BallEvent event)
Scorecard getScorecard(matchId)
List<Match> getLiveMatches()

# Focus Areas:

Live score updates.
Ball-by-ball commentary.
Match state management.
Concurrent match handling.
