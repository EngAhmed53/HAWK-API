package com.shouman.hawkAPI.service

import com.shouman.hawkAPI.exception.NoContentException
import com.shouman.hawkAPI.exception.ResourceNotFoundException
import com.shouman.hawkAPI.model.Company
import com.shouman.hawkAPI.model.Salesman
import com.shouman.hawkAPI.model.User
import com.shouman.hawkAPI.model.UserType
import com.shouman.hawkAPI.repository.CompaniesRepo
import com.shouman.hawkAPI.repository.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


interface CompaniesService {
    fun getAllCompanies(): MutableList<Company>
}

@Service
class CompaniesServiceImpl : CompaniesService {

    @Autowired
    lateinit var companiesRepo: CompaniesRepo

    override fun getAllCompanies(): MutableList<Company> {
        return companiesRepo.findAll()
    }

//    override fun getCompanyByFirebaseUID(firebaseUID: String): Company? {
//        return companiesRepo.getCompanyByFirebaseUID(firebaseUID)
//    }
}


interface UsersService {
    fun addNewUser(user: User): User
    fun updateUserInfo(firebaseUID: String, newUserInfo: Any): User
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

    override fun addNewUser(user: User): User {
        return usersRepo.save(user)
    }

    override fun updateUserInfo(firebaseUID: String, newUserInfo: Any): User {
        if (!usersRepo.existsByFirebaseUID(firebaseUID))
            throw ResourceNotFoundException("firebase id '$firebaseUID' is not found")

        when (newUserInfo) {
            is Company -> {
                val user = usersRepo.getUserByFirebaseUID(firebaseUID)

                user.company?.let {
                    it.companyName = newUserInfo.companyName
                    it.ownerName = newUserInfo.ownerName
                    it.ownerPhoneNumber = newUserInfo.ownerPhoneNumber
                    companiesRepo.save(user.company!!)
                    return user
                }
                println("company is null add new one")
                user.company = newUserInfo
                user.type = UserType.COMPANY
                return usersRepo.save(user)
            }
            is Salesman -> {
                val user = usersRepo.getUserByFirebaseUID(firebaseUID)
                user.salesman = newUserInfo
                user.type = UserType.SALESMAN
                return usersRepo.save(user)
            }
            else -> {
                throw NoContentException("new user info is not valid")
            }
        }
    }
}
