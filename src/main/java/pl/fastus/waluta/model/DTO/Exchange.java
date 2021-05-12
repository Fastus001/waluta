package pl.fastus.waluta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Tom - 12.05.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exchange {
    private String table;
    private String currency;
    private String code;
    private List<ExchangeRate> rates;
}
