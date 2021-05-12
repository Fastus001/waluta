package pl.fastus.waluta.model.DTO;

import lombok.Builder;
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

    @Builder
    public TableDTO(String table, String no, String effectiveDate, List<RateDTO> rates) {
        this.table = table;
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }
}
