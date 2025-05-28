package com.development.softwarebooks.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "softwarebooks")
@Primary
public class DiscountProperties {

    private double price;
    private Map<Integer, Double> discounts;
    private Titles titles;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<Integer, Double> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Map<Integer, Double> discounts) {
        this.discounts = discounts;
    }

    public Titles getTitles() {
        return titles;
    }

    public void setTitles(Titles titles) {
        this.titles = titles;
    }

    // Inner class for book titles
    public static class Titles {
        private String cleanCode;
        private String cleanCoder;
        private String cleanArchitecture;
        private String tdd;
        private String legacyCode;
        private String book6;

        public String getCleanCode() {
            return cleanCode;
        }

        public void setCleanCode(String cleanCode) {
            this.cleanCode = cleanCode;
        }

        public String getCleanCoder() {
            return cleanCoder;
        }

        public void setCleanCoder(String cleanCoder) {
            this.cleanCoder = cleanCoder;
        }

        public String getCleanArchitecture() {
            return cleanArchitecture;
        }

        public void setCleanArchitecture(String cleanArchitecture) {
            this.cleanArchitecture = cleanArchitecture;
        }

        public String getTdd() {
            return tdd;
        }

        public void setTdd(String tdd) {
            this.tdd = tdd;
        }

        public String getLegacyCode() {
            return legacyCode;
        }

        public void setLegacyCode(String legacyCode) {
            this.legacyCode = legacyCode;
        }

        public String getBook6() {
            return book6;
        }

        public void setBook6(String book6) {
            this.book6 = book6;
        }
    }
}
