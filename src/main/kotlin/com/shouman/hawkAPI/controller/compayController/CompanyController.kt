package com.shouman.hawkAPI.controller.compayController

import com.shouman.hawkAPI.model.databaseModels.Company
import com.shouman.hawkAPI.service.companyService.CompaniesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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