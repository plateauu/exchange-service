package dev.insidemind.exchange.service.rating

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.matching.EqualToPattern
import com.github.tomakehurst.wiremock.matching.UrlPattern
import dev.insidemind.exchange.model.NbpApiClientException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import spock.lang.Specification

import javax.inject.Inject

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static com.github.tomakehurst.wiremock.http.RequestMethod.GET
import static com.github.tomakehurst.wiremock.matching.RequestPatternBuilder.newRequestPattern

@MicronautTest
class NbpApiClientTest extends Specification implements TestPropertyProvider {

    private static WireMockServer wireMockServer

    private static int port

    void setupSpec() {
        configureFor("localhost", port);
    }

    void cleanupSpec() {
        wireMockServer.shutdown()
    }

    void cleanup() {
        wireMockServer.resetAll()
    }

    @Inject
    private NbpApiClient client

    def "should return currency rating"() {
        stubFor(get(urlEqualTo(client.ENDPOINT))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(response())
                ))

        when:
        def rating = client.requestForUsdRating()

        then:
        verify(1, newRequestPattern(GET, new UrlPattern(new EqualToPattern(client.ENDPOINT), false)))

        rating.code == 'USD'
        rating.rates.first().ask == '3.7777'
    }

    def "should throw when error response is fetched"() {
        stubFor(get(urlEqualTo(client.ENDPOINT))
                .willReturn(
                        aResponse()
                                .withStatus(404)
                ))

        when:
        client.requestForUsdRating()

        then:
        verify(1, newRequestPattern(GET, new UrlPattern(new EqualToPattern(client.ENDPOINT), false)))

        and:
        thrown NbpApiClientException
    }

    def "should throw when empty response is fetched"() {
        stubFor(get(urlEqualTo(client.ENDPOINT))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{}")

                ))

        when:
        client.requestForUsdRating()

        then:
        verify(1, newRequestPattern(GET, new UrlPattern(new EqualToPattern(client.ENDPOINT), false)))

        and:
        def ex = thrown NbpApiClientException
        ex.message == 'No rating fetched'
    }

    static String response() {
        """
            {
               "code" : "USD",
               "currency" : "dolar amerykaÅski",
               "rates" : [
                  {
                     "ask" : 3.7777,
                     "bid" : 3.7029,
                     "effectiveDate" : "2020-07-31",
                     "no" : "148/C/NBP/2020"
                  }
               ],
               "table" : "C"
        }
        """.trim()
    }

    @Override
    Map<String, String> getProperties() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort())
        wireMockServer.start()
        port = wireMockServer.port()
        return ['bank.api.nbp.url': "http://localhost:$port"]
    }
}
