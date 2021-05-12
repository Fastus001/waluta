package pl.fastus.waluta.mappers;

import org.mapstruct.Mapper;
import pl.fastus.waluta.model.DTO.AvailableRate;
import pl.fastus.waluta.model.Rate;

/**
 * Created by Tom - 12.05.2021
 */
@Mapper(componentModel = "spring")
public interface RateToAvailableRate {

    AvailableRate mapToAvailableRateResponse(Rate rate);
}
