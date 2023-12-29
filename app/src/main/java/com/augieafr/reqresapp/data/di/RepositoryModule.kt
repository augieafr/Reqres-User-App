package com.augieafr.reqresapp.data.di

import com.augieafr.reqresapp.data.local.preference.UserPreference
import com.augieafr.reqresapp.data.network.ApiService
import com.augieafr.reqresapp.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideAuthRepository(apiService: ApiService, userPreference: UserPreference): AuthRepository {
        return AuthRepository(apiService, userPreference)
    }
}