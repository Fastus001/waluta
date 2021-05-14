package pl.fastus.waluta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Exchange {

    @Id
    @GeneratedValue
    private Long id;

    private Double amount;
    private String fromCurrency;
    private String toCurrency;
    private Double amountToReturn;

    @CreationTimestamp
    private LocalDateTime timeStamp;
}
