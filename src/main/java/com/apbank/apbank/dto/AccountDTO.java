package com.apbank.apbank.dto;

import com.apbank.apbank.enuns.AccountType;
import lombok.NonNull;

public record AccountDTO(@NonNull AccountType accountType, @NonNull Long idClient, boolean active) {
}
