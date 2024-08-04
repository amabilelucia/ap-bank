package com.apbank.apbank.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
    Long account;
    TransactionDTO transactionDTO;
}
