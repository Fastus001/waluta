package pl.fastus.waluta.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Rate {

    @Id
    @GeneratedValue
    private Long id;

    private String currency;
    private String code;
    private BigDecimal mid;

    @Builder
    public Rate(String currency, String code, BigDecimal mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }
}
