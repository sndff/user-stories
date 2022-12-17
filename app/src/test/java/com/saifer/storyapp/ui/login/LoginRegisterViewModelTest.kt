package com.saifer.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.saifer.storyapp.data.repositories.LoginRegisterRepository
import com.saifer.storyapp.ui.DataDummy
import com.saifer.storyapp.ui.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRegisterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: LoginRegisterRepository

    private lateinit var viewModel: LoginRegisterViewModel

    private val dummyLogin = DataDummy.generateDummyDataLogin()

    private val dummyRegister = DataDummy.generateDummyDataRegister()

    private val dummyToken = DataDummy.generateDummyToken()


    @Before
    fun setup(){
        viewModel = LoginRegisterViewModel(repository)
    }

    @Test
    fun `When Login Success Token Should not Null`() {
        val expectedToken = MutableLiveData<String>()
        expectedToken.value = dummyToken

        `when`(repository.login(dummyLogin.email, dummyLogin.password)).thenReturn(expectedToken)

        val actualToken = viewModel.login(dummyLogin.email, dummyLogin.password).getOrAwaitValue()
        Mockito.verify(repository).login(dummyLogin.email, dummyLogin.password)
        assertNotNull(actualToken)
        assertTrue(actualToken != null)
    }

    @Test
    fun `When Login Failed Token Should Null`(){
        val expectedToken = MutableLiveData<String>()
        expectedToken.value = null

        `when`(repository.login(dummyLogin.email, dummyLogin.password)).thenReturn(expectedToken)

        val actualToken = viewModel.login(dummyLogin.email, dummyLogin.password).getOrAwaitValue()

        Mockito.verify(repository).login(dummyLogin.email, dummyLogin.password)
        assertNull(actualToken)
        assertTrue(actualToken == null)
    }

    @Test
    fun `When Register Success Return True`(){
        val expectedResult = MutableLiveData<Boolean>()
        expectedResult.value = true

        `when`(repository.register(dummyRegister.name, dummyRegister.email, dummyRegister.password)).thenReturn(expectedResult)

        val actualRegister = viewModel.register(dummyRegister.name, dummyRegister.email, dummyRegister.password).getOrAwaitValue()
        Mockito.verify(repository).register(dummyRegister.name, dummyRegister.email, dummyRegister.password)
        assertNotNull(actualRegister)
        assertTrue(actualRegister)
    }

    @Test
    fun `When Register Failed Return False`(){
        val expectedResult = MutableLiveData<Boolean>()
        expectedResult.value = false
        `when`(repository.register(dummyRegister.name, dummyRegister.email, dummyRegister.password)).thenReturn(expectedResult)

        val actualRegister = viewModel.register(dummyRegister.name, dummyRegister.email, dummyRegister.password).getOrAwaitValue()
        Mockito.verify(repository).register(dummyRegister.name, dummyRegister.email, dummyRegister.password)
        assertNotNull(actualRegister)
        assertTrue(!actualRegister)
    }
}