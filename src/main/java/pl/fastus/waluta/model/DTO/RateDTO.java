package pl.fastus.waluta.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RateDTO {
    private String currency;
    private String code;
    private Double mid;
}
