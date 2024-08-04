package com.apbank.apbank.dto;

import lombok.NonNull;

public record ClientDTO(
        @NonNull String cpf,
        @NonNull String name,
        @NonNull String email,
        boolean active
) {
}
