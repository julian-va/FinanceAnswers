package jva.cloud.financeanswers.utils

object ConstantApp {
    //koin
    const val QUALIFIER_LLM_API_CLIENT = "LlmApiHttpClient"

    //ktor-fake-api-config
    const val BASE_URL_HOST_LLM_API = "api.escuelajs.co"
    const val REQUEST_TIMEOUT_MILLIS = 5000L
    const val CONNECT_TIMEOUT_MILLIS = 5000L
    const val SOCKET_TIMEOUT_MILLIS = 5000L
    const val KTOR_LOGGER = "Ktor Logger"

    //ktor-llm-api
    const val ENDPOINT_LLM_API = "/api/v1/products"
}