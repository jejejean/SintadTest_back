package com.SintadTest.shared.interfaces;

import java.util.Optional;

@FunctionalInterface
public interface LastNumberProvider {
    Optional<String> findLastNumber();
}
