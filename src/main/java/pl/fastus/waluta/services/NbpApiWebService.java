package pl.fastus.waluta.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.fastus.waluta.model.DTO.Exchange;
import pl.fastus.waluta.model.DTO.TableRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class NbpApiWebService {
    private final String apiTableA;
    private final String tableALastDay;

    public NbpApiWebService(@Value("${api.tableA}") String apiTableA,
                            @Value("${api.tableALastDay}") String tableALastDay) {
        this.apiTableA = apiTableA;
        this.tableALastDay = tableALastDay;
    }

    public Flux<TableRequest> getTodayTableACourses(){
        return WebClient
                .create(apiTableA)
                .get()
                .uri(UriBuilder::build)
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response-> {
                    if(response.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Flux.empty();
                    }
                    return response.bodyToFlux(TableRequest.class);
                });
    }

    public Flux<TableRequest> getPreviousDayTableACourses(){
        return WebClient
                .create(tableALastDay)
                .get()
                .uri(UriBuilder::build)
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response->response.bodyToFlux(TableRequest.class));
    }
}
