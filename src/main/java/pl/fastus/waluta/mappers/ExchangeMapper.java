package pl.fastus.waluta.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.fastus.waluta.model.DTO.ExchangeRequest;
import pl.fastus.waluta.model.DTO.ExchangeResponse;
import pl.fastus.waluta.model.Exchange;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    @Mapping(source = "request.amount", target = "amount")
    @Mapping(source = "request.codeFrom", target = "fromCurrency")
    @Mapping(source = "request.codeTo", target = "toCurrency")
    @Mapping(source = "amount", target = "amountToReturn")
    Exchange toExchange(ExchangeRequest request, String amount);
}
