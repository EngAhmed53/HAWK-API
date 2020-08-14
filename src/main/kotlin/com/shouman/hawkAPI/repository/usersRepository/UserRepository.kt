package com.shouman.hawkAPI.repository.usersRepository

import com.shouman.hawkAPI.model.databaseModels.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UsersRepo : JpaRepository<User, Long> {

    @Query("SELECT * FROM users WHERE firebase_uid = :firebaseUID", nativeQuery = true)
    fun getUserByFirebaseUID(@Param("firebaseUID") firebaseUID: String): User

    fun existsByFirebaseUID(firebaseUID: String): Boolean
}
