package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.repositories.TableRepository;

@RequiredArgsConstructor
@Service
public class CurrenciesService {

    private final TableRepository repository;

    public Currencies saveTable(Currencies toSave){
        return repository.save(toSave);
    }
}
