package com.android.my_first_kmp_app.network.clients

import com.android.my_first_kmp_app.feature.models.todos.Todo
import com.android.my_first_kmp_app.network.routes.APIRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

open class TodoAPIClient(private val client: HttpClient) {
    open suspend fun fetchTodos(): List<Todo> {
        return client.get {
            url {
                appendPathSegments(APIRoutes.TODOS)
            }
        }.body()
    }
}