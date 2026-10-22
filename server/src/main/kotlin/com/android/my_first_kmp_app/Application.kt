package com.android.my_first_kmp_app

import com.android.my_first_kmp_app.di.initKoin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initKoin()
    routing {
//        get("/") {
//            call.respondText(sayHello("Ktor"))
//        }
    }
}