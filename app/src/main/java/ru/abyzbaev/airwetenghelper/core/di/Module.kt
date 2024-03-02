package ru.abyzbaev.airwetenghelper.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.abyzbaev.airwetenghelper.autentification.domain.AuthRepository
import ru.abyzbaev.airwetenghelper.autentification.data.AuthRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class Module {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}