package pl.fastus.waluta.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.waluta.mappers.TableRequestMapper;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.TableRequest;
import pl.fastus.waluta.repositories.TableRepository;

@RequiredArgsConstructor
@Service
public class CurrenciesService {

    private final TableRequestMapper mapper;
    private final TableRepository repository;

    public Currencies saveTable(TableRequest toSave){
        final Currencies currencies = mapper.tableRequestToCurrencies(toSave);
        return repository.save(currencies);
    }
}
