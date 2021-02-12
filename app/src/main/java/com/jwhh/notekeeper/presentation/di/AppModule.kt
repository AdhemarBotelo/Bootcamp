package com.jwhh.notekeeper.presentation.di


import androidx.room.Room
import com.jwhh.notekeeper.data.api.RemoteNewsApi
import com.jwhh.notekeeper.data.db.NewsDatabase
import com.jwhh.notekeeper.data.entities.NewsDataEntityMapper
import com.jwhh.notekeeper.data.entities.NewsEntityDataMapper
import com.jwhh.notekeeper.data.repository.NewsCacheImpl
import com.jwhh.notekeeper.data.repository.NewsRemoteImpl
import com.jwhh.notekeeper.data.repository.NewsRepositoryImpl
import com.jwhh.notekeeper.domain.repositories.NewsRepository
import com.jwhh.notekeeper.domain.usecases.GetNewsUseCase
import com.jwhh.notekeeper.presentation.common.AsyncFlowableTransformer
import com.jwhh.notekeeper.presentation.mappers.NewsEntityMapper
import com.jwhh.notekeeper.presentation.news.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import org.koin.android.viewmodel.ext.koin.viewModel


import retrofit2.Retrofit

val mRepositoryModules = module {
    single(name = "remote") { NewsRemoteImpl(api = get(API)) }
    single(name = "local") {
        NewsCacheImpl(database = get(DATABASE), entityToDataMapper = NewsEntityDataMapper(),
                dataToEntityMapper = NewsDataEntityMapper())
    }
    single { NewsRepositoryImpl(remote = get("remote"), cache = get("local")) as NewsRepository }
}

val mUseCaseModules = module {
    factory(name = "getNewsUseCase") { GetNewsUseCase(transformer = AsyncFlowableTransformer(), repositories = get()) }
}

val mNetworkModules = module {
    single(name = RETROFIT_INSTANCE) { createNetworkClient(BASE_URL) }
    single(name = API) { (get(RETROFIT_INSTANCE) as Retrofit).create(RemoteNewsApi::class.java) }
}

val mLocalModules = module {
    single(name = DATABASE) { Room.databaseBuilder(androidApplication(), NewsDatabase::class.java, "news_articles").build() }
}

val mViewModels = module {
    viewModel {
        NewsViewModel(getNewsUseCase = get(GET_NEWS_USECASE), mapper = NewsEntityMapper())
    }
}

private const val BASE_URL = "https://newsapi.org/v2/"
private const val RETROFIT_INSTANCE = "Retrofit"
private const val API = "Api"
private const val GET_NEWS_USECASE = "getNewsUseCase"
private const val REMOTE = "remote response"
private const val DATABASE = "database"