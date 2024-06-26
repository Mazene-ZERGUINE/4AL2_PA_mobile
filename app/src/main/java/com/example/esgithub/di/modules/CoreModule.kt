package com.example.esgithub.di.modules
import com.example.esgithub.repositories.auth.AuthRepository
import com.example.esgithub.ui.viewModels.LoginViewModel
import com.example.esgithub.ui.viewModels.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val coreModule =
    module {

        viewModel { LoginViewModel(get()) }

        viewModel { SignupViewModel(get()) }

        single { AuthRepository(get()) }
    }
