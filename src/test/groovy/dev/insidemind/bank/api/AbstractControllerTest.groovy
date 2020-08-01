package dev.insidemind.bank.api


import dev.insidemind.bank.api.model.CreateAccountRequest
import dev.insidemind.bank.api.model.ExchangeRequest
import dev.insidemind.bank.api.model.WebAmount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.repository.AccountRepository
import dev.insidemind.bank.service.CurrencyRating
import dev.insidemind.bank.service.CurrencyRatingService
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.Specification

import javax.inject.Inject
import java.time.LocalDateTime

@MicronautTest
class AbstractControllerTest extends Specification {

    protected static final String NAME = 'testName'
    protected static final String SURE_NAME = 'testSurname'
    protected static final String PESEL = '42052061347'
    protected static final WebAmount AMOUNT = new WebAmount("100,0")

    private static final CurrencyRating PLN_TO_USD_RATING = new CurrencyRating(0.5g)
    private static final CurrencyRating USD_TO_PLN_RATING = new CurrencyRating(2.0g)

    @Inject
    @Client("/")
    protected HttpClient client

    @Inject
    private AccountRepository repository

    @MockBean(CurrencyRatingService)
    CurrencyRatingService ratingService() {
        Mock(CurrencyRatingService) {
            getCurrentPlnToUsdRating() >> PLN_TO_USD_RATING
            getCurrentUsdToPlnRating() >> USD_TO_PLN_RATING
        }
    }

    void setup() {
        repository.database.clear()
    }

    protected createAccount() {
        def createRequest = new CreateAccountRequest(NAME, SURE_NAME, PESEL, AMOUNT)
        client
                .toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, "/account").body(createRequest), TestResponse.class)
    }

    protected HttpResponse<ExchangeTestResponse> exchange(String url) {
        def request = new ExchangeRequest(PESEL, new WebAmount('10'))
        client
                .toBlocking()
                .exchange(HttpRequest.PUT(url, request), ExchangeTestResponse.class)
    }

    static class TestResponse {
        String accountId
        Map<Currency, String> subAccounts
    }

    static class ExchangeTestResponse extends TestResponse {
        LocalDateTime operationDate
    }

}
