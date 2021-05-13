package pl.fastus.waluta.model.DTO;

import lombok.Value;

@Value
public class ExchangeRequest {
    Double amount;
    String codeFrom;
    String codeTo;
}
