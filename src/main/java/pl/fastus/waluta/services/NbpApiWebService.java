package pl.fastus.waluta.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.fastus.waluta.model.DTO.TableDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NbpApiWebService {
    private final String apiTableA;

    public NbpApiWebService(@Value("${api.tableA}") String apiTableA) {
        this.apiTableA = apiTableA;
    }

    public Flux<TableDTO> getTodayTableACourses(){
        return WebClient
                .create(apiTableA)
                .get()
                .uri(UriBuilder::build)
                .accept( MediaType.APPLICATION_JSON )
                .exchangeToFlux(response->response.bodyToFlux(TableDTO.class));
    }


}
