package com.vasberc.presentation.componets.addlistdialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasberc.domain.model.DomainResult
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListDialog(
    viewModel: AddListViewModel = koinViewModel(),
    onDismissRequest: () -> Unit
) {
    val result by viewModel.result.collectAsStateWithLifecycle()
    val errorMessage by remember {
        derivedStateOf {
            when (result) {
                is DomainResult.Success -> {
                    viewModel.resetResult()
                    onDismissRequest()
                    null
                }
                is DomainResult.Error -> (result as DomainResult.Error).message
                else -> null
            }
        }
    }
    val text by viewModel.textInput.collectAsStateWithLifecycle()
    AddListDialogContent(
        text = text,
        errorMessage = errorMessage,
        onDismissRequest = {
            viewModel.resetResult()
            onDismissRequest()
        },
        onCreate = {
            viewModel.addList()
        },
        onValueChange = {
            viewModel.onValueChange(it)
        },
        onError = {
            viewModel.onError(it)
        }
    )
}

@Composable
private fun AddListDialogContent(
    errorMessage: String?,
    text: String,
    onDismissRequest: () -> Unit,
    onCreate: () -> Unit,
    onValueChange: (String) -> Unit,
    onError: (String?) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val name = text.trim()
                // quick local validation before attempting insertion
                if (name.isEmpty()) {
                    onError("Name cannot be empty")
                    return@TextButton
                }
                // call ViewModel to add list; result will be observed (DB will return constraint error on duplicates)
                onCreate()
            }) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancel") }
        },
        title = { Text("Create new list") },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        onValueChange(it)
                        onError(null)
                                    },
                    label = { Text("List name") },
                    isError = errorMessage != null,
                    modifier = Modifier.fillMaxWidth()
                )
                errorMessage?.let { err ->
                    Text(text = err, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}