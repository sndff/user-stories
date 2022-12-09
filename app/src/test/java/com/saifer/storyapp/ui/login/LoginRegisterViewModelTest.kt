package com.saifer.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saifer.storyapp.data.repositories.LoginRegisterRepository
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginRegisterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var loginRegisterRepository: LoginRegisterRepository

    private lateinit var viewModel: LoginRegisterViewModel

    private val dummyLogin = DataDummy.generateDummyLoginData()

    private val dummyRegister = DataDummy.generateDummyRegisterData()

    @Before
    fun setUp(){
        viewModel = LoginRegisterViewModel(loginRegisterRepository)
    }

    @Test
    fun `When Success Login`() = runTest {
        loginRegisterRepository.doLogin(
            dummyLogin.email,
            dummyLogin.password
        )
        Mockito.verify(loginRegisterRepository).doLogin(
            dummyLogin.email,
            dummyLogin.password
        )
    }

    @Test
    fun `When Success Register`() = runTest {
        viewModel.register(
            dummyRegister.name,
            dummyRegister.email,
            dummyRegister.password
        )
        Mockito.verify(loginRegisterRepository).doRegister(
            dummyRegister.name,
            dummyRegister.email,
            dummyRegister.password
        )
    }
}