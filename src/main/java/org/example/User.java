package org.example;

import java.time.LocalDate;

public record User(String name, int age, boolean blocked, LocalDate birthDate) {
}
