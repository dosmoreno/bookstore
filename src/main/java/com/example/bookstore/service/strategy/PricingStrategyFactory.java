package com.example.bookstore.service.strategy;

import com.example.bookstore.entity.BookType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class PricingStrategyFactory {

    private final Map<BookType, PricingStrategy> strategyMap = new EnumMap<>(BookType.class);

    public PricingStrategyFactory() {
        strategyMap.put(BookType.NEW_RELEASE, new NewReleasePricing());
        strategyMap.put(BookType.REGULAR, new RegularPricing());
        strategyMap.put(BookType.OLD_EDITION, new OldEditionPricing());
    }

    public PricingStrategy getStrategy(BookType bookType) {
        return strategyMap.get(bookType);
    }
}
