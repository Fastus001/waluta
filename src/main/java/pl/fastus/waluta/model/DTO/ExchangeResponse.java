package pl.fastus.waluta.model.DTO;

import lombok.Value;

@Value
public class ExchangeResponse {
    Double amount;
    String codeFrom;
    String codeTo;
    String amountAfterConversion;
}
