package com.vasberc.domain.usecase

import com.vasberc.domain.repo.ListRepo
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
//todo this use case will work correct when the files will stored in the db an we retrieve them as flow wit their relations
@Factory
class GetFilesOfFolderUseCase(
    private val musicFilesRepo: MusicFilesRepo,
    private val listRepo: ListRepo
) {
    operator fun invoke(
        folderPath: String
    ) = musicFilesRepo.getFilesOfFolderFlow(folderPath).map {
        it?.copy(
            files = it.files.map {
                it.copy(
                    listsAdded = listRepo.getListsOfFile(it.filePath).first()
                )
            }
        )
    }
}