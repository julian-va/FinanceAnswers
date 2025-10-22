package jva.cloud.financeanswers

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform