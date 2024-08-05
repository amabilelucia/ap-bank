package com.apbank.apbank.controller;

import com.apbank.apbank.dto.RefundDTO;
import com.apbank.apbank.dto.RefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "Refund-Service", url = "${spring.endpoints.refund-service}")
public interface RefundClient {
    @PostMapping("/refund")
    RefundResponse postRefund(@RequestBody RefundDTO refundDTO);
}
