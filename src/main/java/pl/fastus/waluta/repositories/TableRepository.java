package pl.fastus.waluta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fastus.waluta.model.Currencies;

public interface TableRepository extends JpaRepository<Currencies, Long> {
}
