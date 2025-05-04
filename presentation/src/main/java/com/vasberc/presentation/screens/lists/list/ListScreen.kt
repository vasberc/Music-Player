package com.vasberc.presentation.screens.lists.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vasberc.domain.model.MusicModel
import com.vasberc.presentation.componets.RequestPermissionBaseScreen
import com.vasberc.presentation.screens.home.folder.FolderScreenContent
import com.vasberc.presentation.utils.MusicPlayer
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SelectedListScreen(
    listName: String,
    viewModel: SelectedListViewModel = koinViewModel(),
    musicPlayer: MusicPlayer = koinInject()
) {
    RequestPermissionBaseScreen(
        onPermissionGrantedContent = {
            val folder by viewModel.folder.collectAsStateWithLifecycle()
            val isPlaying by musicPlayer.isPlaying.collectAsStateWithLifecycle()
            val playingSong: MusicModel? by musicPlayer.playingSongIndex.collectAsStateWithLifecycle()

            FolderScreenContent(
                name = listName,
                folder = folder,
                playingSong = playingSong,
                isPaused = !isPlaying,
                onFileClick = { index ->
                    folder?.let {
                        musicPlayer.setCurrentFolder(it)
                    }
                    musicPlayer.actionPlay(index)
                },
                onAddToPlaylist = { index, category ->
                    viewModel.addToPlaylist(index, category)
                }
            )
        }
    )
}