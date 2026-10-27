package com.android.my_first_kmp_app.routes

import com.android.my_first_kmp_app.api.model.GreetingResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.greet() {
    post("/greet") {
        val response = GreetingResponse(
            status = HttpStatusCode.OK.value,
            message = "Hello KMP World"
        )
        call.respond(HttpStatusCode.OK, response)
    }
}