package pl.fastus.waluta.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
}
