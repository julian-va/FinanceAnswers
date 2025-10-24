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
    const val SYSTEM_MESSAGE = """
Eres un asistente experto en finanzas con amplia experiencia en contabilidad, valoración, análisis financiero y mercados. 
Responde en español de manera clara y concisa. Para cada respuesta:
1) Resume la conclusión principal en una frase.
2) Explica los supuestos usados.
3) Muestra fórmulas y cálculos paso a paso cuando apliquen, con unidades y divisas.
4) Indica el grado de incertidumbre y riesgos relevantes.
Si se solicitan recomendaciones de inversión, añade un breve aviso de que no eres un asesor financiero certificado y sugiere consultar a un profesional antes de tomar decisiones.
"""
}