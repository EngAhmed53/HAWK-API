package com.shouman.hawkAPI.service.customerService

import com.shouman.hawkAPI.model.*
import com.shouman.hawkAPI.model.databaseModels.Customer
import com.shouman.hawkAPI.model.databaseModels.toExCustomer
import com.shouman.hawkAPI.model.externalModels.ExternalCustomer
import com.shouman.hawkAPI.repository.branchesRepository.BranchesRepo
import com.shouman.hawkAPI.repository.companiesRepository.CompaniesRepo
import com.shouman.hawkAPI.repository.customerRepository.CustomersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


interface CustomersService {

    fun getCompanyCustomers(companyId: Long): MutableSet<Customer>
    fun addCustomerToCompany(exCustomer: ExternalCustomer,
                             companyId: Long,
                             branchId: Long,
                             salesmanId: Long?): ResponseEntity<ServerResponse<ExternalCustomer?>>

}

@Service
class CustomersServiceImpl : CustomersService {

    @Autowired
    private lateinit var customersRepo: CustomersRepo

    @Autowired
    private lateinit var companiesRepo: CompaniesRepo

    @Autowired
    private lateinit var branchesRepo: BranchesRepo

    //@Autowired
    //lateinit var salesmanRepo: SalesmanRepo


    override fun getCompanyCustomers(companyId: Long): MutableSet<Customer> {
        return customersRepo.getAllCompanyCustomers(companyId)
    }

    override fun addCustomerToCompany(exCustomer: ExternalCustomer,
                                      companyId: Long,
                                      branchId: Long,
                                      salesmanId: Long?): ResponseEntity<ServerResponse<ExternalCustomer?>> {

        //check if company id is exist and valid
        if (!companiesRepo.existsById(companyId)) {
            return ResponseEntity(
                    ServerResponse<ExternalCustomer?>(ResponseCode.COMPANY_ID_NOT_VALID, null),
                    HttpStatus.BAD_REQUEST)
        }

        //check if branch id is exist and valid
        if (!branchesRepo.existsById(branchId)) {
            return ResponseEntity(
                    ServerResponse<ExternalCustomer?>(ResponseCode.BRANCH_ID_NOT_VALID, null),
                    HttpStatus.BAD_REQUEST)
        }

        //check if salesman id is exist and valid
        //if ()

        //all is good so lets save the customer to the database

        val entityCustomer = Customer(
                exCustomer.id,
                exCustomer.createTime,
                exCustomer.createdBy,
                companiesRepo.findById(companyId).get(), branchesRepo.findById(branchId).get(), null,
                exCustomer.customerName, exCustomer.superVisor,
                exCustomer.phoneNum, exCustomer.email,
                exCustomer.latitude, exCustomer.longitude,
                exCustomer.visitsSet
        ).apply {
            this.visitsSet.forEach {
                it.customer = this
            }
        }
        return ResponseEntity(
                ServerResponse<ExternalCustomer?>(ResponseCode.SUCCESS, customersRepo.save(entityCustomer).toExCustomer()),
                HttpStatus.OK)
    }
}