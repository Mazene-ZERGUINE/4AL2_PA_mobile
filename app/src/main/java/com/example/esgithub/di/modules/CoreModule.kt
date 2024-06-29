package com.example.esgithub.di.modules
import com.example.esgithub.repositories.AuthRepository
import com.example.esgithub.repositories.ProgramRepository
import com.example.esgithub.repositories.UserRepository
import com.example.esgithub.ui.viewModels.CommentViewModel
import com.example.esgithub.ui.viewModels.LoginViewModel
import com.example.esgithub.ui.viewModels.ProfileViewModel
import com.example.esgithub.ui.viewModels.ProgramViewModel
import com.example.esgithub.ui.viewModels.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val coreModule =
    module {

        viewModel { LoginViewModel(get()) }

        viewModel { SignupViewModel(get()) }

        viewModel { ProgramViewModel(get()) }

        viewModel { ProfileViewModel(get()) }

        viewModel { CommentViewModel(get()) }

        single { AuthRepository(get()) }
        single { ProgramRepository(get()) }
        single { UserRepository(get()) }
    }
