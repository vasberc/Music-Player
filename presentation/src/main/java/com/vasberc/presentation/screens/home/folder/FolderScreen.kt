package com.vasberc.presentation.screens.home.folder

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.presentation.componets.MusicFileUiItem
import com.vasberc.presentation.componets.ToolbarUiItem
import com.vasberc.presentation.utils.MusicPlayer
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun FolderScreen(
    folderName: String,
    viewModel: FolderViewModel = koinViewModel(),
    musicPlayer: MusicPlayer = koinInject()
) {
    val folder by viewModel.folder.collectAsStateWithLifecycle()
    val isPlaying by musicPlayer.isPlaying.collectAsStateWithLifecycle()
    val playingSong: MusicModel? by musicPlayer.playingSongIndex.collectAsStateWithLifecycle()

    FolderScreenContent(
        name = folderName,
        folder = folder,
        playingSong = playingSong,
        isPaused = !isPlaying,
        onFileClick = { index ->
            folder?.let {
                musicPlayer.setCurrentFolder(it)
            }
            musicPlayer.actionPlay(index)
        }
    )
}


@Composable
fun FolderScreenContent(
    name: String,
    folder: FolderModel?,
    playingSong: MusicModel?,
    isPaused: Boolean,
    onFileClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ToolbarUiItem(name)
        when {
            folder == null -> {
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

            folder.files.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No files found.")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(20.dp, 0.dp)
                ) {
                    items(folder.files.size, { index -> folder.files[index].fileName }) { index ->
                        val item = folder.files[index]
                        MusicFileUiItem(
                            item = item,
                            onClick = { onFileClick(index) },
                            isPlaying = playingSong == item,
                            isPaused = isPaused
                        )
                        val isLastItem by remember {
                            derivedStateOf {
                                index == folder.files.size - 1
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