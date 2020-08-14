package com.shouman.hawkAPI.service.companyService

import com.shouman.hawkAPI.model.databaseModels.Company
import com.shouman.hawkAPI.repository.companiesRepository.CompaniesRepo
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

