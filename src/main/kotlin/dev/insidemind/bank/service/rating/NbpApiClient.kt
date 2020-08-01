package dev.insidemind.bank.service.rating

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import dev.insidemind.bank.model.NbpApiClientException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NbpApiClient {

    private val logger: Logger = LoggerFactory.getLogger(NbpApiClient::class.java)

    private val request: NbpRequest = {
        httpClient
                .toBlocking()
                .exchange(HttpRequest.GET<Void>("/exchangerates/rates/c/usd/"), NbpApiResponse::class.java)
    }

    private val requestLogger = { httpRequest: NbpRequest ->
        logger.debug("Sending NBP request...")
        val (response, duration) = measureTimeInMillis { httpRequest() }
        logger.debug("Request returned in $duration ms")
        response
    }

    @field:Client("\${bank.api.nbp.url}")
    @Inject
    lateinit var httpClient: RxHttpClient

    fun requestForUsdRating(): NbpApiResponse {
        val response = requestLogger(request)

        if (response.status >= HttpStatus.BAD_REQUEST)
            throw NbpApiClientException("Exception trying fetch currency rating. Reason: ${response.reason()}")

        return response.body()!!
    }
}

typealias NbpRequest = () -> HttpResponse<NbpApiResponse>

fun <T> measureTimeInMillis(block: () -> T): Pair<T, Long> {
    val start = System.currentTimeMillis()
    val value = block()
    val time = System.currentTimeMillis() - start
    return value to time
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class NbpApiResponse(
        val code: String,
        val rates: List<NbpRatesResponse>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NbpRatesResponse(
        val ask: String
)
