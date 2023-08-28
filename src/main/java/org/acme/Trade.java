package org.acme;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trade {
    private LocalDateTime tradeDate;
    private LocalDate purchaseDate;
    private LocalDate repurchaseDate;
    private int quantity;
    private double repoRate;
    private String collateral;
    private String initiator;
    private String responder;

    public Trade(LocalDateTime tradeDate, LocalDate purchaseDate, LocalDate repurchaseDate, int quantity,
            double repoRate, String collateral, String initiator, String responder) {
        this.tradeDate = tradeDate;
        this.purchaseDate = purchaseDate;
        this.repurchaseDate = repurchaseDate;
        this.quantity = quantity;
        this.repoRate = repoRate;
        this.collateral = collateral;
        this.initiator = initiator;
        this.responder = responder;
    }

    public LocalDateTime getTradeDate() {
        return tradeDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getRepurchaseDate() {
        return repurchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getRepoRate() {
        return repoRate;
    }

    public String getCollateral() {
        return collateral;
    }

    public String getInitiator() {
        return initiator;
    }

    public String getResponder() {
        return responder;
    }
}
