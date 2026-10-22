package com.android.my_first_kmp_app.feature.models.todos

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
	val id: Int? = null,
	val completed: Boolean? = null,
	val title: String? = null,
	val userId: Int? = null
)
