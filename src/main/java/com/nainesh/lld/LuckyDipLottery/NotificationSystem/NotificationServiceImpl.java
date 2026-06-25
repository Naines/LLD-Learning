package com.nainesh.lld.LuckyDipLottery.NotificationSystem;


import com.nainesh.lld.LuckyDipLottery.entity.User;

/**
 * @author Nainesh
 */
public class NotificationServiceImpl implements NotificationService {
    public void notifyWinner(User user, int seatNo, String link) {
        System.out.printf("📧 %s | 📱 %s → Seat #%d | %s%n", user.email, user.phone, seatNo, link);
    }
}
