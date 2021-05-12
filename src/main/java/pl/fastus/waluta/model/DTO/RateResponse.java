package pl.fastus.waluta.model.DTO;

import lombok.Value;

/**
 * Created by Tom - 12.05.2021
 */
@Value
public class RateResponse {
    String currency;
    String code;
    Double mid;
}
