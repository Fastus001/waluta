package pl.fastus.waluta.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(exclude = "rates")
@Data
@Entity
public class Currencies {

    @Id
    @GeneratedValue
    private Long id;

    private String tableName;
    private String number;
    private String effectiveDate;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencies")
    private Set<Rate> rates = new HashSet<>();

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
        this.rates.forEach(r->r.setCurrencies(this));
    }
}
