package com.learning.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Invoice {

    private Long id;
    private String invoiceId;
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Items> items;

    @Getter
    @Setter
    public static class Items {

        private Long id;
        private String name;
        private String quantity;
        private Double rate;
        private Double amount;
        private Invoice invoiceDetails;
    }

}
