package pl.fastus.waluta.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Currencies {

    @Id
    @GeneratedValue
    private Long id;

    private String tableName;
    private String no;
    private String effectiveDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencies")
    private Set<Rate> rates = new HashSet<>();
}
