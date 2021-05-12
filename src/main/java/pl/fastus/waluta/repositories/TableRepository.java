package pl.fastus.waluta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fastus.waluta.model.Table;

public interface TableRepository extends JpaRepository<Table, Long> {
}
