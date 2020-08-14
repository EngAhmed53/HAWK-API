package com.shouman.hawkAPI.service.branchServices

import com.shouman.hawkAPI.model.ResponseCode
import com.shouman.hawkAPI.model.ServerResponse
import com.shouman.hawkAPI.model.databaseModels.Branch
import com.shouman.hawkAPI.repository.branchesRepository.BranchesRepo
import com.shouman.hawkAPI.repository.companiesRepository.CompaniesRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface BranchesServices {
    fun addNewBranchToCompany(companyId: Long, branch: Branch): ResponseEntity<ServerResponse<Branch?>>
    fun getCompanyBranches(companyId: Long): MutableSet<Branch>
}

@Service
class BranchesServicesImpl : BranchesServices {

    @Autowired
    private lateinit var branchesRepo: BranchesRepo

    @Autowired
    private lateinit var companiesRepo: CompaniesRepo

    override fun addNewBranchToCompany(companyId: Long, branch: Branch): ResponseEntity<ServerResponse<Branch?>> {
        if (!companiesRepo.existsById(companyId)) {
            return ResponseEntity(
                    ServerResponse<Branch?>(ResponseCode.COMPANY_ID_NOT_VALID, null),
                    HttpStatus.BAD_REQUEST)
        }

        //get the company from the table
        val company = companiesRepo.findById(companyId).get()

        //add the company to branch
        branch.company = company

        //return the saved branch
        return ResponseEntity(
                ServerResponse<Branch?>(ResponseCode.SUCCESS, branchesRepo.save(branch)),
                HttpStatus.OK)
    }

    override fun getCompanyBranches(companyId: Long): MutableSet<Branch> {
        //return the company branches set
        return companiesRepo.findById(companyId).get().branchesSet
    }
}