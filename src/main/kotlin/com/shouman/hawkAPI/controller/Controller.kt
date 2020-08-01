package com.shouman.hawkAPI.controller

import com.shouman.hawkAPI.exception.NoContentException
import com.shouman.hawkAPI.exception.ResourceNotFoundException
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
    fun addNewUser(@RequestBody user: User?): User? {
        user?.let { return usersService.addNewUser(it) }
        throw NoContentException("null values is not accepted")
    }

    @GetMapping("/users/{firebaseUID}")
    fun getUserByFirebaseUID(@PathVariable firebaseUID: String?): ResponseEntity<ServerResponse<User?>> {

        firebaseUID?.let {
            if (!usersService.existsByFirebaseUID(firebaseUID)) {

                return ResponseEntity(
                        ServerResponse<User?>(false, ResponseCode.FIREBASE_CODE_NOT_VALID, null),
                        HttpStatus.NOT_FOUND)

            } else {
                return ResponseEntity(
                        ServerResponse<User?>(true,
                                ResponseCode.SUCCESS,
                                usersService.getUserByFirebaseUID(it)),
                        HttpStatus.OK)
            }
        }
        throw ResourceNotFoundException("firebaseUID is not found")
    }

    @GetMapping("/users")
    fun getAllUsers(): MutableList<User> {
        return usersService.getAllUsers()
    }

    @PutMapping("/users/{firebaseUID}")
    fun updateUserInfo(@PathVariable firebaseUID: String?, @RequestBody newUserInfo: Company?): User? {
        firebaseUID?.let { return newUserInfo?.let { it1 -> usersService.updateUserInfo(it, it1) } }
        throw ResourceNotFoundException("firebaseUID is not found")
    }
}