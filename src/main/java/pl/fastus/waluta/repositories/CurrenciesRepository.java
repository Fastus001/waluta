package pl.fastus.waluta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fastus.waluta.model.Currencies;

public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {
}
