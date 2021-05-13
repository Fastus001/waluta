package pl.fastus.waluta.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.ExchangeResponse;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    @Mapping(source = "request.amount", target = "amount")
    @Mapping(source = "amount", target = "amountAfterConversion")
    ExchangeResponse toExchangeResponse(ExchangeRequest request, String amount);
}
