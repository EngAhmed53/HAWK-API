package com.shouman.hawkAPI.repository

import com.shouman.hawkAPI.model.Company
import com.shouman.hawkAPI.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestBody

@Repository
interface UsersRepo : JpaRepository<User, Long> {

    @Query("SELECT * FROM users WHERE firebase_uid = :firebaseUID", nativeQuery = true)
    fun getUserByFirebaseUID(@Param("firebaseUID") firebaseUID: String): User

    fun existsByFirebaseUID(firebaseUID: String): Boolean
}

@Repository
interface CompaniesRepo : JpaRepository<Company, Long> {

//    @Query("SELECT * FROM companies WHERE company_firebase_uid = :firebaseUID", nativeQuery = true)
//    fun getCompanyByFirebaseUID(@Param("firebaseUID") firebaseUID: String): Company?
}