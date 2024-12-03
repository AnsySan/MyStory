package com.clone.twitter.payment_service.mapper;

import com.clone.twitter.payment_service.dto.PaymentDto;
import com.clone.twitter.payment_service.entity.Payment;
import com.clone.twitter.payment_service.event.PaymentEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PaymentMapper {

    Payment toEntity(PaymentDto dto);

    PaymentDto toDto(Payment entity);

    @Mapping(target = "type", source = "status")
    PaymentEvent toEvent(Payment payment);
}