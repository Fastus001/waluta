package pl.fastus.waluta.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Table {

    @Id
    @GeneratedValue
    private Long id;

    private String table;
    private String no;
    private String effectiveDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rate> rates;
}
