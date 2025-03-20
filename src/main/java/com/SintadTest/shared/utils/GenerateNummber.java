package com.SintadTest.shared.utils;

import com.SintadTest.shared.interfaces.LastNumberProvider;

import java.time.Year;

public class GenerateNummber {

    public static String generateNextNumber(LastNumberProvider lastNumberProvider) {
        int currentYear = Year.now().getValue() % 100;

        String lastServiceNumber = lastNumberProvider.findLastNumber()
                .orElse("0000-" + currentYear);

        String[] parts = lastServiceNumber.split("-");
        int lastSequentialNumber = Integer.parseInt(parts[0]);
        int newSequentialNumber = lastSequentialNumber + 1;

        return String.format("%04d-%02d", newSequentialNumber, currentYear);
    }
}
