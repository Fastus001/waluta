package pl.fastus.waluta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Tom - 12.05.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRate {
    private String no;
    private String effectiveDate;
    private Double mid;
}
