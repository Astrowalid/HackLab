package com.example.hacklab.data.repository

import com.example.hacklab.data.User
import com.example.hacklab.data.dao.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun saveUser(email: String, username: String) {
        userDao.logoutAllUsers()

        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            userDao.updateUser(existingUser.copy(isLoggedIn = true, username = username))
        } else {
            userDao.insertUser(User(email = email, username = username, isLoggedIn = true))
        }
    }

    suspend fun getLoggedInUser(): User? {
        return userDao.getLoggedInUser()
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun logoutUser() {
        userDao.logoutAllUsers()
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun isUserLoggedIn(): Boolean {
        return userDao.getLoggedInUser() != null
    }
}