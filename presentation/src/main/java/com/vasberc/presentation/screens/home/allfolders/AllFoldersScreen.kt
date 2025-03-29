package com.vasberc.presentation.screens.home.allfolders

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.vasberc.domain.model.FolderModel
import com.vasberc.presentation.componets.FolderUiItem
import com.vasberc.presentation.componets.ToolbarUiItem
import com.vasberc.presentation.navigation.homenavigation.HomeRoute
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AllFoldersScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navHostController: NavHostController
) {
    val files = viewModel.files.collectAsStateWithLifecycle().value
    val permissions = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO  // Android 13+
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE  // Older versions
        }
    )

    LaunchedEffect(Unit) {
        if (permissions.status != PermissionStatus.Granted) {
            permissions.launchPermissionRequest()
        }
    }

    LaunchedEffect(permissions.status) {
        if (permissions.status == PermissionStatus.Granted) {
            viewModel.getFiles()
        }
    }

    HomeScreenContent(
        files = files,
        refreshFolders = viewModel::refreshFolders,
        onFolderClick = {
            navHostController.navigate(
                HomeRoute.Folder(
                    it.name,
                    it.path
                )
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    files: List<FolderModel>?,
    onFolderClick: (FolderModel) -> Unit,
    refreshFolders: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ToolbarUiItem("Device files")
        when {
            files == null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Loading...")
                }
            }

            files.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No files found.")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp, 0.dp)
                ) {
                    items(files.size, { index -> files[index].name }) { index ->

                        FolderUiItem(
                            item = files[index],
                            onClick = { onFolderClick(files[index]) }
                        )
                        val isLastItem by remember {
                            derivedStateOf {
                                index == files.size - 1
                            }
                        }
                        if (!isLastItem) {
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }
            }
        }
    }
}