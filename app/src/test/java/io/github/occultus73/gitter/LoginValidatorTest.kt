package io.github.occultus73.gitter

import android.util.Log
import com.example.unittesting.login.model.LoginValidator

import org.junit.Assert
import org.junit.Test

class LoginValidatorTest {

    val objectForTest = LoginValidator()

    @Test
    fun `empty login is invalid`() {
        val result = objectForTest.validateLogin("")
        Assert.assertEquals(false, result)
    }

    @Test
    fun `not empty login is valid`() {
        val result = objectForTest.validateLogin("debintom")
        Assert.assertEquals(true, result)
    }

    @Test
    fun `invalid email format login`() {
        var result = objectForTest.isLoginDataValid("debintom", "123")
        Assert.assertEquals(0, result)
    }

    @Test
    fun `valid email format login`() {
        val result = objectForTest.isLoginDataValid("debintom@gmail.com","12345678")
        System.out.println("${result}")
        Assert.assertEquals(-1, result)
    }

    @Test
    fun `empty password is invalid`() {
        val result = objectForTest.validatePassword("")
        Assert.assertEquals(false, result)
    }

    @Test
    fun `password is invalid if shorter then limit`() {
        val result = objectForTest.validatePassword("123456")
        Assert.assertEquals(false, result)
    }

    @Test
    fun `password is valid if equal to limit`() {
        val result = objectForTest.validatePassword("12345678")
        Assert.assertEquals(true, result)

    }

    @Test
    fun `password is valid if longer than limit`() {
        val result = objectForTest.validatePassword("123456789")
        Assert.assertEquals(true, result)
    }
}