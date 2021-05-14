package pl.fastus.waluta.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.fastus.waluta.model.DTO.TableRequest;

@Service
public class NbpApiService {
    private final String apiTableA;

    public NbpApiService(@Value("${api.tableA}") String apiTableA) {
        this.apiTableA = apiTableA;
    }

    public TableRequest getTableA(){
        return WebClient
                .create(apiTableA)
                .get()
                .uri(UriBuilder::build)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(TableRequest.class))
                .blockFirst();
    }
}
