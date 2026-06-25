package com.nainesh.lld.LuckyDipLottery.AllocationService;

import com.nainesh.lld.LuckyDipLottery.entity.User;
import com.nainesh.lld.LuckyDipLottery.enums.LotteryConstants;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nainesh
 */
public class AllocationService {
    // Key: roundId, Value: Set of user aadharHashes who won that round
    private final Map<Integer, Set<String>> winHistory = new ConcurrentHashMap<>();

    public boolean isEligible(User user, int currentRound) {
        int count =1;
//        System.out.println("here");
        for (int r = currentRound - 1; r > currentRound - 1 - LotteryConstants.EXCLUSION_ROUNDS && r > 0; r--) {
            Set<String> winners = winHistory.get(r);
            if (winners != null && winners.contains(user.aadharHash)) count++;
        }
//        System.out.println(user.email+" "+currentRound+" "+count);
        return count<=5;
    }

    public void recordWin(User user, int round) {
        winHistory.computeIfAbsent(round, k -> ConcurrentHashMap.newKeySet()).add(user.aadharHash);
    }

    public void clearOldRounds(int currentRound) {
        winHistory.entrySet().removeIf(e -> currentRound - e.getKey() > 10);
    }
}
