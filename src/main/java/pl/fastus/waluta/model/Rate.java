package pl.fastus.waluta.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Data
@Entity
public class Rate {

    @Id
    @GeneratedValue
    private Long id;

    private String currency;
    private String code;
    private Double mid;

    @ManyToOne
    private Currencies currencies;

    @Builder
    public Rate(String currency, String code, Double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }
}
