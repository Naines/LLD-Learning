package com.nainesh.lld.LuckyDipLottery.NotificationSystem;


import com.nainesh.lld.LuckyDipLottery.entity.User;

public interface NotificationService {
    void notifyWinner(User user, int seatNo, String link);
}