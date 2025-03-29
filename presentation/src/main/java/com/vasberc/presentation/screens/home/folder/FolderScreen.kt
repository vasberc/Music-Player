package com.vasberc.presentation.screens.home.folder

import android.media.MediaPlayer
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
import androidx.compose.runtime.DisposableEffect
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
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.presentation.componets.MusicFileUiItem
import com.vasberc.presentation.componets.ToolbarUiItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FolderScreen(
    folderName: String,
    viewModel: FolderViewModel = koinViewModel()
) {
    val folder = viewModel.folder.collectAsStateWithLifecycle().value
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var playingSong: MusicModel? by remember { mutableStateOf(null) }

    LaunchedEffect(playingSong) {
        if (playingSong != null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(playingSong?.filePath)
                prepare()
                start()
                setOnCompletionListener {
                    val index =
                        folder?.files?.indexOf(playingSong)?.takeUnless { it == folder.files.size - 1 } ?: -1
                    mediaPlayer?.release()
                    mediaPlayer = null
                    playingSong = if (index != -1) {
                        folder?.files?.get(index + 1)
                    } else {
                        null
                    }
                }
            }
        }
    }

    FolderScreenContent(
        name = folderName,
        folder = folder,
        playingSong = playingSong,
        onFileClick = {
            if (playingSong != null) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                playingSong = null
            }
            playingSong = it
        }
    )

    DisposableEffect(null) {
        onDispose {
            if (playingSong != null) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                playingSong = null
            }
        }
    }
}


@Composable
fun FolderScreenContent(
    name: String,
    folder: FolderModel?,
    playingSong: MusicModel?,
    onFileClick: (MusicModel) -> Unit
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
                            onClick = { onFileClick(item) },
                            isPlaying = playingSong == item
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