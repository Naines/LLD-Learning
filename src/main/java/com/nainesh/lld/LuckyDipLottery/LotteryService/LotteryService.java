package com.nainesh.lld.LuckyDipLottery.LotteryService;


import com.nainesh.lld.LuckyDipLottery.AllocationService.AllocationService;
import com.nainesh.lld.LuckyDipLottery.NotificationSystem.NotificationService;
import com.nainesh.lld.LuckyDipLottery.entity.Ticket;
import com.nainesh.lld.LuckyDipLottery.entity.User;
import com.nainesh.lld.LuckyDipLottery.enums.LotteryConstants;
import com.nainesh.lld.LuckyDipLottery.enums.TicketStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class LotteryService {
    private final BlockingQueue<User> queue = new ArrayBlockingQueue<>(2);
    private final Ticket[] seats = new Ticket[LotteryConstants.TOTAL_SEATS];
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final NotificationService notifier;
    private final AllocationService allocator;

    private int round = 0;
    private boolean open = false;

    public LotteryService(NotificationService notifier, AllocationService allocator) {
        this.notifier = notifier;
        this.allocator = allocator;
        for (int i = 0; i < LotteryConstants.TOTAL_SEATS; i++)
            seats[i] = new Ticket(i + 1);

        // Single scheduler thread checks all seats every 30s
        scheduler.scheduleAtFixedRate(
                this::checkAllExpirations,
                LotteryConstants.EXPIRY_CHECK_INTERVAL_MS,
                LotteryConstants.EXPIRY_CHECK_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    public synchronized void openRound(int round) {
        if (open) throw new IllegalStateException("Round already open");
        this.round = round;
        this.open = true;
        queue.clear();
        allocator.clearOldRounds(round);
        System.out.println("🔓 Round " + round + " OPENED (" + LotteryConstants.TOTAL_SEATS + " seats)");
    }

    public synchronized void closeAndDraw() {
        open = false;
        List<User> pool = new ArrayList<>();
        queue.drainTo(pool);
        Collections.shuffle(pool);
        System.out.println("🎲 Drawing from " + pool.size() + " participants...");
        for (int i = 0; i < LotteryConstants.TOTAL_SEATS && i < pool.size(); i++)
            allocate(i, pool.get(i));
    }

    public synchronized void endRound() {
        for (Ticket t : seats)
            if (t.status == TicketStatus.RESERVED)
                expire(t.seatNo - 1);
        System.out.println("🏁 Round " + round + " ENDED");
    }

    public boolean takeTicket(User user) {
        if (!open) {
            System.out.println("❌ Round not open"); return false;
        }
        if (!allocator.isEligible(user, round)) {
            System.out.println("❌ Excluded (won in last " + LotteryConstants.EXCLUSION_ROUNDS + " rounds): " + user.aadharHash.substring(0, 8));
            return false;
        }
        boolean added = queue.offer(user);
        if (added) System.out.println("✅ " + user.aadharHash.substring(0, 8) + " joined queue (" + queue.size() + "/" + LotteryConstants.TOTAL_SEATS + ")");
        else System.out.println("❌ Queue full");
        return added;
    }

    public boolean pay(String aadharHash, int seatNo) {
        Ticket t = seats[seatNo - 1];
        synchronized (t) {
            System.out.println(t.status+" "+t.seatNo+" "+seatNo+" "+t.holder.aadharHash+" "+aadharHash);
            if (t.status != TicketStatus.RESERVED || !t.holder.aadharHash.equals(aadharHash)) {
                System.out.println("❌ Invalid pay attempt for seat " + seatNo); return false;
            }
            if (System.currentTimeMillis() > t.deadlineMs) {
                System.out.println("❌ Payment expired for seat " + seatNo);
                return false;
            }
            t.status = TicketStatus.PAID;
            System.out.println("✅ Seat " + seatNo + " PAID");
            return true;
        }
    }

    public void printStatus() {
        long paid = Arrays.stream(seats).filter(s -> s.status == TicketStatus.PAID).count();
        long reserved = Arrays.stream(seats).filter(s -> s.status == TicketStatus.RESERVED).count();
        long available = Arrays.stream(seats).filter(s -> s.status == TicketStatus.AVAILABLE).count();
        System.out.println("📊 Status: Paid=" + paid + " Reserved=" + reserved + " Available=" + available);
    }

    private void allocate(int idx, User user) {
        Ticket t = seats[idx];
        synchronized (t) {
            t.holder = user;
            t.status = TicketStatus.RESERVED;
            t.deadlineMs = System.currentTimeMillis() + LotteryConstants.PAYMENT_WINDOW_MS;
            allocator.recordWin(user, round);
            notifier.notifyWinner(user, t.seatNo, LotteryConstants.PAYMENT_LINK_BASE + t.seatNo);
        }
    }

    // Single method checks ALL seats every 30s — no per-seat scheduler threads
    private void checkAllExpirations() {
        long now = System.currentTimeMillis();
        for (int i = 0; i < LotteryConstants.TOTAL_SEATS; i++) {
            Ticket t = seats[i];
            synchronized (t) {
                if (t.status == TicketStatus.RESERVED && now > t.deadlineMs) {
                    expire(i);
                }
            }
        }
    }

    private void expire(int idx) {
        Ticket t = seats[idx];
        System.out.println("⏰ Seat " + (idx + 1) + " EXPIRED for " + t.holder.aadharHash.substring(0, 8));
        t.status = TicketStatus.EXPIRED;
        User next = queue.poll();
        if (next != null) {
            System.out.println("🔄 Reallocating seat " + (idx + 1) + " to " + next.aadharHash.substring(0, 8));
            t.status = TicketStatus.AVAILABLE;
            allocate(idx, next);
        } else {
            t.holder = null;
            t.status = TicketStatus.AVAILABLE;
        }
    }
}