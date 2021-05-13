package pl.fastus.waluta.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.fastus.waluta.model.DTO.TableRequest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@Service
public class NbpApiWebService {
    private final String apiTableA;
    private final String tableALastDay;
    private TableRequest currentDayTable;

    public NbpApiWebService(@Value("${api.tableA}") String apiTableA,
                            @Value("${api.tableALastDay}") String tableALastDay) {
        this.apiTableA = apiTableA;
        this.tableALastDay = tableALastDay;
    }

    public TableRequest getTodayTableACourses(){
        if(currentDayTable!=null && LocalDate.now().toString().equals(currentDayTable.getEffectiveDate())){
            return currentDayTable;
        }
        currentDayTable = WebClient
                .create(apiTableA)
                .get()
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response-> {
                    if(response.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Flux.empty();
                    }
                    return response.bodyToFlux(TableRequest.class);
                }).blockFirst();
        return currentDayTable;
    }

    public TableRequest getPreviousDayTableACourses(){
        return WebClient
                .create(tableALastDay)
                .get()
                .uri(UriBuilder::build)
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response->response.bodyToFlux(TableRequest.class))
                .blockFirst();
    }

}
