package com.nainesh.lld.LuckyDipLottery;

import com.nainesh.lld.LuckyDipLottery.AllocationService.AllocationService;
import com.nainesh.lld.LuckyDipLottery.LotteryService.LotteryService;
import com.nainesh.lld.LuckyDipLottery.NotificationSystem.NotificationService;
import com.nainesh.lld.LuckyDipLottery.NotificationSystem.NotificationServiceImpl;
import com.nainesh.lld.LuckyDipLottery.entity.User;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Nainesh
 *
 * 1. create request
 * 2. filter request
 * 3. allocate lottery
 * 4. Pick winner
 * 5. send notification
 * 6. If not reply in sometime, pick new winner and send again - scheduler to check the status of winner
 *
 */
public class Main {

    static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static NotificationService notifier;

    public static void main(String[] args)  {
        try{
            NotificationService notifier = new NotificationServiceImpl();
            AllocationService allocator = new AllocationService();
            LotteryService lottery = new LotteryService(notifier, allocator);

//            System.out.println("=== TEST 1: Basic Round (10 users, 10 seats) === ");
            lottery.openRound(1);
            User user1 = new User("nainesh", "ngoel.main@gmail.com", "12345");
            User user2=new User("arnav", "agoel.main@gmail.com", "67891");
            User[] users = new User[10];
//            for (int i = 0; i < 10; i++) {
//                //aadharId, email, phoneNumber
//                users[i] = new User(String.format("%04d", i), "u" + i + "@x.com", "9000" + i);
//                lottery.takeTicket(users[i]);
//            }
            lottery.takeTicket(user1);
            lottery.takeTicket(user2);
            lottery.closeAndDraw();
            Thread.sleep(15000); // let notifications print

            // Pay for seats 1, 3, 5
//            for(int i=0;i<10;i++) System.out.println(users[i].aadharHash);
//            lottery.pay(users[0].aadharHash, 1);
//            lottery.pay(users[2].aadharHash, 3);
//            lottery.pay(users[4].aadharHash, 5);
//            lottery.printStatus();

            for(int r=2;r<=7;r++){
                lottery.openRound(r);
                lottery.takeTicket(user1);
                lottery.closeAndDraw();
            }





        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
