package com.shouman.hawkAPI.service.UserService

import com.shouman.hawkAPI.model.ResponseCode
import com.shouman.hawkAPI.model.ServerResponse
import com.shouman.hawkAPI.model.databaseModels.Company
import com.shouman.hawkAPI.model.databaseModels.User
import com.shouman.hawkAPI.repository.companiesRepository.CompaniesRepo
import com.shouman.hawkAPI.repository.usersRepository.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface UsersService {
    fun addNewUser(user: User): ResponseEntity<ServerResponse<User?>>
    fun updateUserInfo(firebaseUID: String, newUserInfo: Any): ResponseEntity<ServerResponse<User?>>
    fun getUserByFirebaseUID(firebaseUID: String): User
    fun getAllUsers(): MutableList<User>
    fun existsByFirebaseUID(firebaseUID: String): Boolean
}

@Service
class UsersServiceImpl : UsersService {

    @Autowired
    lateinit var usersRepo: UsersRepo

    @Autowired
    lateinit var companiesRepo: CompaniesRepo

    override fun getAllUsers(): MutableList<User> {
        return usersRepo.findAll()
    }

    override fun existsByFirebaseUID(firebaseUID: String): Boolean {
        return usersRepo.existsByFirebaseUID(firebaseUID)
    }

    override fun getUserByFirebaseUID(firebaseUID: String): User {
        return usersRepo.getUserByFirebaseUID(firebaseUID)
    }

    override fun addNewUser(user: User): ResponseEntity<ServerResponse<User?>> {
        // if the is already exist in the database
        // by his firebaseUID this mean that this
        // user was logged out and he need to log in again
        // we return the same user and not creating any new users
        if (existsByFirebaseUID(user.firebaseUID)) {
            return ResponseEntity(
                    ServerResponse<User?>(ResponseCode.SUCCESS, getUserByFirebaseUID(user.firebaseUID)),
                    HttpStatus.OK)
        }
        // if the user is not found we create new one
        return ResponseEntity(
                ServerResponse<User?>(ResponseCode.SUCCESS, usersRepo.save(user)),
                HttpStatus.OK)
    }

    override fun updateUserInfo(firebaseUID: String, newUserInfo: Any)
            : ResponseEntity<ServerResponse<User?>> {
        if (!usersRepo.existsByFirebaseUID(firebaseUID)) {
            // user is not found in database
            return ResponseEntity(
                    ServerResponse<User?>(ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                    HttpStatus.BAD_REQUEST)
        }

        when (newUserInfo) {
            is Company -> {
                val user = usersRepo.getUserByFirebaseUID(firebaseUID)
                user.company?.let {
                    it.companyName = newUserInfo.companyName
                    it.ownerName = newUserInfo.ownerName
                    it.ownerPhoneNumber = newUserInfo.ownerPhoneNumber
                    it.imgURL = newUserInfo.imgURL
                    it.user = user
                    user.company = it
                    return ResponseEntity(
                            ServerResponse<User?>(ResponseCode.SUCCESS, usersRepo.save(user)),
                            HttpStatus.OK)

                }
                user.company = newUserInfo
                newUserInfo.user = user
                return ResponseEntity(
                        ServerResponse<User?>(ResponseCode.SUCCESS, usersRepo.save(user)),
                        HttpStatus.OK)
//                user.company?.let {
//                    it.companyName = newUserInfo.companyName
//                    it.ownerName = newUserInfo.ownerName
//                    it.ownerPhoneNumber = newUserInfo.ownerPhoneNumber
//                    companiesRepo.save(user.company!!)
//                    return user
//                }
//                println("company is null add new one")
//                user.company = newUserInfo
//                user.type = UserType.COMPANY
//                return usersRepo.save(user)
            }
//            is Salesman -> {
//                val user = usersRepo.getUserByFirebaseUID(firebaseUID)
//                user.salesman = newUserInfo
//                user.type = UserType.SALESMAN
//                return usersRepo.save(user)
//            }
            else -> {
                return ResponseEntity(
                        ServerResponse<User?>(ResponseCode.NEW_USER_INFO_NOT_VALID, null),
                        HttpStatus.BAD_REQUEST)
            }
        }
    }
}