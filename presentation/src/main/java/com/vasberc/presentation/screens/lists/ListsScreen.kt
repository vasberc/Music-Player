package com.vasberc.presentation.screens.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vasberc.presentation.componets.ListUiItem
import com.vasberc.presentation.componets.ToolbarUiItem
import com.vasberc.presentation.navigation.listsnavigation.ListsRoute

@Composable
fun ListScreen(
    navHostController: NavHostController,
    viewModel: ListsViewModel = koinViewModel()
) {
    val lists by viewModel.lists.collectAsStateWithLifecycle()
    ListScreenContent(
        lists = lists,
        onItemSelected = {
            navHostController.navigate(ListsRoute.List(it))
        }
    )
}

@Composable
fun ListScreenContent(
    lists: List<String>?,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ToolbarUiItem("Lists")
        when {
            lists == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
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
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No lists found.")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreenContent(
        lists = listOf("List 1", "List 2", "List 3"),
        onItemSelected = {}
    )
}