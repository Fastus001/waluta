package pl.fastus.waluta.mappers;

import org.junit.jupiter.api.Test;
import pl.fastus.waluta.model.DTO.RateResponse;
import pl.fastus.waluta.model.Rate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RateToRateResponseMapperTest {

    public static final String BAT_TAJLANDIA = "bat (Tajlandia)";
    public static final String THB = "THB";
    public static final double MID = 0.1201;
    RateToRateResponseMapper mapper = new RateToRateResponseMapperImpl();

    @Test
    void rateToRateResponse() {
        Rate rate= new Rate("bat (Tajlandia)", "THB", 0.1201);

        final RateResponse rateResponse = mapper.mapToRateResponse(rate);

        assertNotNull(rateResponse);
        assertEquals(BAT_TAJLANDIA, rateResponse.getCurrency());
        assertEquals(THB, rateResponse.getCode());
        assertEquals(MID, rateResponse.getMid());
    }
}
