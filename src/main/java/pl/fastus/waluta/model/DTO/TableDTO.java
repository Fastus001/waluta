package pl.fastus.waluta.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TableDTO {

    private String table;
    private String no;
    private String effectiveDate;
    private List<RateDTO> rates;
}
