package com.vasberc.presentation.componets.listcategoriesdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasberc.domain.model.MusicModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListCategoriesDialog(
    item: MusicModel?,
    onDismiss: () -> Unit,
    onItemAdded: (String) -> Unit,
    onItemRemoved: (String) -> Unit
) {
    val isVisible by remember(item) {
        derivedStateOf {
            item != null
        }
    }

    if (isVisible) {
        val viewModel: ListCategoriesDialogViewModel = koinViewModel()
        val categories by viewModel.categories.collectAsStateWithLifecycle()
        ListCategoriesDialogContent(categories, item, onDismiss) { category, isAdded ->
            if (isAdded) {
                viewModel.removeCategory(category, item)
                onItemRemoved(category)
            } else {
                viewModel.addCategory(category, item)
                onItemAdded(category)
            }
        }
    }
}

@Composable
fun ListCategoriesDialogContent(
    categoryList: List<String>?,
    item: MusicModel?,
    onDismiss: () -> Unit,
    onItemSelected: (String, Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        when (categoryList) {
            null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Loading...")
                }
            }

            else -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape.copy(CornerSize(8.dp)))
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(15.dp),
                        text = "Add to List",
                        textAlign = TextAlign.Center
                    )

                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(20.dp, 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(categoryList.size, { index -> categoryList[index] }) { index ->
                            val isFirstItem = index == 0
                            val isLastItem = index == categoryList.size - 1
                            val isAdded = item?.listsAdded?.any { it == categoryList[index] } == true
                            if (isFirstItem) {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(5.dp)
                                    .clickable {
                                        onItemSelected(categoryList[index], isAdded)
                                    }
                            ) {
                                Text(text = categoryList[index])
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    imageVector = if (isAdded)
                                        Icons.Default.RemoveCircleOutline
                                    else
                                        Icons.Default.AddCircleOutline,
                                    contentDescription = if (isAdded)
                                        "Click to remove"
                                    else
                                        "Click to add",
                                    tint = if (isAdded)
                                        Color.Red
                                    else
                                        Color.Green
                                )
                            }
                            if (!isLastItem) {
                                Spacer(modifier = Modifier.height(10.dp))
                            } else {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListCategoriesDialogPreview() {
    ListCategoriesDialogContent(
        categoryList = listOf("Category 1", "Category 2", "Category 3"),
        item = MusicModel(
            fileName = "",
            filePath = "",
            title = "",
            artist = "",
            album = "",
            duration = 0,
            size = 0,
            listsAdded = listOf("Category 1")
        ),
        {},
        {_, _ -> }
    )
}
