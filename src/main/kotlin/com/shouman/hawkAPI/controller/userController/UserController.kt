package com.shouman.hawkAPI.controller.userController

import com.shouman.hawkAPI.model.ResponseCode
import com.shouman.hawkAPI.model.ServerResponse
import com.shouman.hawkAPI.model.databaseModels.Company
import com.shouman.hawkAPI.model.databaseModels.User
import com.shouman.hawkAPI.service.UserService.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hawk")
class UsersController {

    @Autowired
    lateinit var usersService: UsersService

    @PostMapping("/users")
    fun addNewUser(@RequestBody user: User?): ResponseEntity<ServerResponse<User?>> {
        user?.let { return usersService.addNewUser(it) }
        return ResponseEntity(
                ServerResponse<User?>(ResponseCode.NEW_USER_INFO_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/users/{firebaseUID}")
    fun getUserByFirebaseUID(@PathVariable firebaseUID: String?): ResponseEntity<ServerResponse<User?>> {

        firebaseUID?.let {
            if (!usersService.existsByFirebaseUID(firebaseUID)) {

                return ResponseEntity(
                        ServerResponse<User?>(ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                        HttpStatus.NOT_FOUND)

            } else {
                return ResponseEntity(
                        ServerResponse<User?>(
                                ResponseCode.SUCCESS,
                                usersService.getUserByFirebaseUID(it)),
                        HttpStatus.OK)
            }
        }
        return ResponseEntity(
                ServerResponse<User?>(ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/users")
    fun getAllUsers(): MutableList<User> {
        return usersService.getAllUsers()
    }

    @PutMapping("/users/companies/{firebaseUID}")
    fun updateUserInfo(@PathVariable firebaseUID: String?, @RequestBody newUserInfo: Company?): ResponseEntity<ServerResponse<User?>>? {
        firebaseUID?.let { uid ->
            return newUserInfo?.let { com -> usersService.updateUserInfo(uid, com) }
        }
        return ResponseEntity(
                ServerResponse<User?>(ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }
}