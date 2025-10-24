package jva.cloud.financeanswers.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import jva.cloud.financeanswers.data.remote.repository.LlmRemoteRepositoryImpl
import jva.cloud.financeanswers.domain.repository.LlmRemoteRepository
import jva.cloud.financeanswers.domain.usecase.RetrieverAnswersFromLlmStreamsUseCase
import jva.cloud.financeanswers.domain.usecase.impl.RetrieverAnswersFromLlmStreamsUseCaseImpl
import jva.cloud.financeanswers.presentation.viewmodel.financeAnswers.FinanceAnswersViewModel
import jva.cloud.financeanswers.utils.ConstantApp.BASE_URL_HOST_LLM_API
import jva.cloud.financeanswers.utils.ConstantApp.CONNECT_TIMEOUT_MILLIS
import jva.cloud.financeanswers.utils.ConstantApp.KTOR_LOGGER
import jva.cloud.financeanswers.utils.ConstantApp.QUALIFIER_LLM_API_CLIENT
import jva.cloud.financeanswers.utils.ConstantApp.REQUEST_TIMEOUT_MILLIS
import jva.cloud.financeanswers.utils.ConstantApp.SOCKET_TIMEOUT_MILLIS
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val clientHttpModule = module {
    single<HttpClient>(named(QUALIFIER_LLM_API_CLIENT)) {
        createHttpClientLlmApi(get())
    }
}

val repositoryModule = module {
    single<LlmRemoteRepository> {
        LlmRemoteRepositoryImpl(get(qualifier = named(QUALIFIER_LLM_API_CLIENT)))
    }
}

val useCaseModule = module {
    single<RetrieverAnswersFromLlmStreamsUseCase> {
        RetrieverAnswersFromLlmStreamsUseCaseImpl(get())
    }
}

val viewModelModule = module {
    viewModel { FinanceAnswersViewModel(get()) }
}
expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule, clientHttpModule, repositoryModule, useCaseModule, viewModelModule
        )
    }
}

private fun createHttpClientLlmApi(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("$KTOR_LOGGER: $message")
                }
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
            connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS
            socketTimeoutMillis = SOCKET_TIMEOUT_MILLIS
        }
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL_HOST_LLM_API
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
    }
}