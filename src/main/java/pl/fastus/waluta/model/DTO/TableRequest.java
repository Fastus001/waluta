package pl.fastus.waluta.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class TableRequest {

    private String table;
    private String no;
    private String effectiveDate;
    private Set<RateRequest> rates = new HashSet<>();
}
