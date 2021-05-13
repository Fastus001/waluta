package pl.fastus.waluta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fastus.waluta.model.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
