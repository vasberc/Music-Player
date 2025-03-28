package com.vasberc.presentation.screens.home.folder

import android.media.MediaPlayer
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun FolderScreen(
    folderName: String,
    viewModel: FolderViewModel = koinViewModel()
) {
    val folder = viewModel.folder.collectAsStateWithLifecycle().value
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isPlaying by remember { mutableStateOf(false) }

    FolderScreenContent(folderName, folder) {
        if (isPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(it.filePath)
            prepare()
            start()
        }
        isPlaying = true
    }

    DisposableEffect(null) {
       onDispose {
           if (isPlaying) {
               mediaPlayer?.stop()
               mediaPlayer?.release()
               mediaPlayer = null
               isPlaying = false
           }
       }
    }
}


@Composable
fun FolderScreenContent(
    name: String,
    folder: FolderModel?,
    onFolderClick: (MusicModel) -> Unit
) {
    when {
        folder == null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Loading...")
            }
        }

        folder.files.isEmpty() -> {
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
                items(folder.files.size, { index -> folder.files[index].fileName }) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onFolderClick(folder.files[index])
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_music_note_24),
                            contentDescription = folder.files[index].fileName,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Text(
                                folder.files[index].fileName
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}