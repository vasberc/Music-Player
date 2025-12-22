package com.vasberc.presentation.screens.lists.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vasberc.presentation.componets.ListUiItem
import com.vasberc.presentation.componets.ToolbarUiItem
import com.vasberc.presentation.componets.addlistdialog.AddListDialog
import com.vasberc.presentation.navigation.listsnavigation.ListsRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    navHostController: NavHostController,
    viewModel: ListsViewModel = koinViewModel()
) {
    val lists by viewModel.lists.collectAsStateWithLifecycle(null)
    // Dialog and FAB states
    val showDialogState = remember { mutableStateOf(false) }

    ListScreenContent(
        lists = lists,
        onItemSelected = {
            navHostController.navigate(ListsRoute.List(it))
        },
        onFabClick = { showDialogState.value = true }
    )

    if (showDialogState.value) {
        AddListDialog(
            onDismissRequest = { showDialogState.value = false }
        )
    }
}

@Composable
fun ListScreenContent(
    lists: List<String>?,
    onItemSelected: (String) -> Unit,
    onFabClick: () -> Unit = {}
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ToolbarUiItem("Lists")

            when {
                lists == null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Loading...")
                    }
                }

                lists.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("No lists found.")
                    }
                }

                else -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(20.dp, 0.dp)
                    ) {
                        items(lists.size, { index -> lists[index] }) { index ->
                            val isFirstItem = index == 0
                            val isLastItem = index == lists.size - 1
                            ListUiItem(
                                listName = lists[index],
                                isLastItem = isLastItem,
                                isFirstItem = isFirstItem,
                                onClick = { onItemSelected(lists[index]) },
                                on3DotsClick = {}
                            )
                            if (!isLastItem) {
                                Spacer(modifier = Modifier.height(1.dp))
                            }
                        }
                    }
                }
            }
        }
        // FAB anchored at bottom end
        Surface(modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd)) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                FloatingActionButton(onClick = onFabClick, modifier = Modifier.padding(16.dp)) {
                    Icon(Icons.Default.Add, contentDescription = "Add list")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreenContent(
        lists = listOf("List 1", "List 2", "List 3"),
        onItemSelected = {}
    )
}