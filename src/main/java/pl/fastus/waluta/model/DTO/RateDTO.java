package pl.fastus.waluta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateDTO {
    private String currency;
    private String code;
    private BigDecimal mid;
}
