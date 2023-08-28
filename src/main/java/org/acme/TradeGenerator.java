package org.acme;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TradeGenerator {

    public static Trade generate() {
        List<String> participants = Arrays.asList(new String[] { "SNB", "UBS", "Raiffeisen", "ZKB" });
        List<String> collaterals = Arrays.asList(new String[] { "SNBGC", "ADDGC", "ROSDAX", "L1CHF" });

        Random rand = new Random();

        LocalDateTime tradeDate = LocalDateTime.now();
        LocalDate purchaseDate = LocalDate.now().minusDays(rand.nextInt(0, 4));
        LocalDate repurchaseDate = purchaseDate.plusDays(rand.nextInt(1, 30));
        int quantity = rand.nextInt(1_000_000, 10_000_000);
        double repoRate = rand.nextDouble(0.03, 0.04);
        String collateral = collaterals.get(rand.nextInt(collaterals.size()));

        Collections.shuffle(participants);
        String initiator = participants.get(0);
        String responder = participants.get(1);

        return new Trade(tradeDate, purchaseDate, repurchaseDate, quantity, repoRate, collateral, initiator, responder);
    }
}
