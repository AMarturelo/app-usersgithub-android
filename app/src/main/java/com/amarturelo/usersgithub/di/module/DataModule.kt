package com.amarturelo.usersgithub.di.module

import com.amarturelo.usersgithub.data.datasource.UserDataSource
import com.amarturelo.usersgithub.data.datasource.remote.UserDataSourceRemote
import com.amarturelo.usersgithub.data.repository.UserRepositoryData
import com.amarturelo.usersgithub.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Reusable
    fun provideUserDataSourceRemote(dataSource: UserDataSourceRemote): UserDataSource {
        return dataSource
    }

    @Provides
    @Reusable
    fun provideUserRepository(repository: UserRepositoryData): UserRepository {
        return repository
    }
}
