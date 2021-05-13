package pl.fastus.waluta.model.DTO;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Value
public class ExchangeRequest {

    public static final String THREE_LETTERS_MAX = "Currency code should have 3 letter! For example PLN";
    @Min(value = 1, message = "Amount have to be 1 and above")
    @Max(value = Long.MAX_VALUE)
    Double amount;

    @Length(min = 3, max = 3, message = THREE_LETTERS_MAX)
    String codeFrom;

    @Length(min = 3, max = 3, message = THREE_LETTERS_MAX)
    String codeTo;
}
