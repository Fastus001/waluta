package pl.fastus.waluta.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.fastus.waluta.model.DTO.Exchange;
import pl.fastus.waluta.model.DTO.TableRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NbpApiWebService {
    private final String apiTableA;
    private final String exchange;

    public NbpApiWebService(@Value("${api.tableA}") String apiTableA,
                            @Value("${api.exchange}") String exchange) {
        this.apiTableA = apiTableA;
        this.exchange = exchange;
    }

    public Flux<TableRequest> getTodayTableACourses(){
        return WebClient
                .create(apiTableA)
                .get()
                .uri(UriBuilder::build)
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response->response.bodyToFlux(TableRequest.class));
    }

    public Mono<Exchange> getTodayExchangeRateFor(String code){
        return WebClient
                .create(exchange)
                .get()
                .uri(UriBuilder::build)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response->response.bodyToMono(Exchange.class));

    }


}
