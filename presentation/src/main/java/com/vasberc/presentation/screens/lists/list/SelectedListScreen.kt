package com.vasberc.presentation.screens.lists.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
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

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                //Load list on resume because it can be on the stack but update from other view
                viewModel.loadList()
            }

            else -> Unit
        }
    }

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
                }
            )
        }
    )
}