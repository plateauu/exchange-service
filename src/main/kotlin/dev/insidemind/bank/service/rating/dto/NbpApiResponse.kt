package dev.insidemind.bank.service.rating.dto

data class NbpApiResponse(
        val code: String,
        val rates: List<NbpRatesResponse>
)
