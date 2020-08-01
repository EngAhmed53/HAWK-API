package com.shouman.hawkAPI.controller

import com.shouman.hawkAPI.model.Company
import com.shouman.hawkAPI.model.ResponseCode
import com.shouman.hawkAPI.model.ServerResponse
import com.shouman.hawkAPI.model.User
import com.shouman.hawkAPI.service.CompaniesService
import com.shouman.hawkAPI.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/hawk")
class CompaniesController {

    @Autowired
    lateinit var companiesService: CompaniesService

//    @PostMapping("/companies")
//    fun addNewCompany(@RequestBody company: Company?): Company? {
//        company?.let { return companiesService.addNewCompany(it) }
//        throw NoContentException("null values is not accepted")
//    }

//    @GetMapping("/companies/{firebaseUID}")
//    fun getCompanyByFirebaseUID(@PathVariable firebaseUID: String?): Company? {
//
//        firebaseUID?.let { return companiesService.getCompanyByFirebaseUID(it) }
//        throw ResourceNotFoundException("firebaseUID is not found")
//    }

    @GetMapping("/companies")
    fun getAllCompanies(): MutableList<Company> {
        return companiesService.getAllCompanies()
    }

}


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

    @PutMapping("/users/{firebaseUID}")
    fun updateUserInfo(@PathVariable firebaseUID: String?, @RequestBody newUserInfo: Company?): ResponseEntity<ServerResponse<User?>>? {
        firebaseUID?.let { uid ->
            return newUserInfo?.let { com -> usersService.updateUserInfo(uid, com) }
        }
        return ResponseEntity(
                ServerResponse<User?>(ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }
}