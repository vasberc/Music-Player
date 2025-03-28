package com.vasberc.presentation.screens.home.allfolders

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.vasberc.domain.model.FolderModel
import com.vasberc.presentation.R
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

    HomeScreenContent(files) {
        navHostController.navigate(
            HomeRoute.Folder(
                it.name,
                it.path
            )
        )
    }

}

@Composable
fun HomeScreenContent(
    files: List<FolderModel>?,
    onFolderClick: (FolderModel) -> Unit
) {
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
                    .padding(20.dp)
            ) {
                items(files.size, { index -> files[index].name }) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onFolderClick(files[index])
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_folder_24),
                            contentDescription = files[index].name,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Text(
                                files[index].name
                            )
                            Text(
                                files[index].count.toString()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}