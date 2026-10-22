package com.android.my_first_kmp_app.ui.todo

import com.android.my_first_kmp_app.ui.common.MToolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.my_first_kmp_app.ui.todo.view.TodoContent
import com.android.my_first_kmp_app.ui.todo.viewmodel.TodoViewModel
import my_first_kmp_app.app.shared.generated.resources.Res
import my_first_kmp_app.app.shared.generated.resources.todoTitle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodoScreen(
    navController: NavController,
    viewModel: TodoViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MToolbar(
                title = stringResource(Res.string.todoTitle),
                onBackClick = {
                    navController.popBackStack()
                },
            )
        },
    ) { innerPadding ->
        TodoContent(
            state = uiState,
            onRetry = viewModel::fetchTodos,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
@Preview
fun TodoScreenPreview() {
    TodoScreen(rememberNavController(), viewModel = koinViewModel())
}