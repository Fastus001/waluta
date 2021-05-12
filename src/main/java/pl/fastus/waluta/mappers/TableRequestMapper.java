package pl.fastus.waluta.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.fastus.waluta.model.Currencies;
import pl.fastus.waluta.model.DTO.TableRequest;

/**
 * Created by Tom - 12.05.2021
 */
@Mapper(componentModel = "spring")
public interface TableRequestMapper {

    @Mappings({
            @Mapping(source = "table", target = "tableName"),
            @Mapping(source = "no", target = "number")
    })
    Currencies tableRequestToCurrencies(TableRequest tableRequest);
}
