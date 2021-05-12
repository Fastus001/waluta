package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.model.Table;
import pl.fastus.waluta.repositories.TableRepository;

@RequiredArgsConstructor
@Service
public class TableService {

    private final TableRepository repository;

    public Table saveTable(Table toSave){
        return repository.save(toSave);
    }
}
