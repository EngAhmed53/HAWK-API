package com.shouman.hawkAPI.controller.branchController



import com.shouman.hawkAPI.model.databaseModels.Branch
import com.shouman.hawkAPI.model.ResponseCode
import com.shouman.hawkAPI.model.ServerResponse
import com.shouman.hawkAPI.service.branchServices.BranchesServicesImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hawk/companies/")
class BranchesController {

    @Autowired
    private lateinit var branchesServicesImpl: BranchesServicesImpl

    @PostMapping("/{companyId}/branches")
    fun addNewBranchToCompany(@PathVariable companyId: Long,
                              @RequestBody branch: Branch?): ResponseEntity<ServerResponse<Branch?>> {

        branch?.let {
            return branchesServicesImpl.addNewBranchToCompany(companyId, it)
        }
        return ResponseEntity(
                ServerResponse<Branch?>(ResponseCode.BRANCH_OBJECT_IS_NULL, null),
                HttpStatus.BAD_REQUEST)
    }


    @GetMapping("/{companyId}/branches")
    fun getAllCompanyBranches(@PathVariable companyId:Long): Set<Branch> {
        return branchesServicesImpl.getCompanyBranches(companyId)
    }
}