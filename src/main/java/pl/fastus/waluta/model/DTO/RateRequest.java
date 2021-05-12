package pl.fastus.waluta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRequest {
    private String currency;
    private String code;
    private Double mid;
}
