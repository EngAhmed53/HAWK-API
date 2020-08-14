package com.shouman.hawkAPI.controller.customerController

import com.shouman.hawkAPI.model.*
import com.shouman.hawkAPI.model.databaseModels.toExCustomer
import com.shouman.hawkAPI.model.externalModels.ExternalCustomer
import com.shouman.hawkAPI.service.customerService.CustomersServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hawk/companies/")
class CustomersController {

    @Autowired
    private lateinit var customersServiceImpl: CustomersServiceImpl

    @PostMapping("/{companyId}/branches/{branchId}/salesmen/{salesmanId}/customers")
    fun addNewCustomerToCompanyBySalesman(@PathVariable companyId: Long,
                                          @PathVariable branchId: Long,
                                          @PathVariable salesmanId: Long,
                                          @RequestBody externalCustomer: ExternalCustomer?)
            : ResponseEntity<ServerResponse<ExternalCustomer?>> {

        externalCustomer?.let {
            return customersServiceImpl.addCustomerToCompany(it, companyId, branchId, salesmanId)
        }

        return ResponseEntity(
                ServerResponse<ExternalCustomer?>(ResponseCode.CUSTOMER_OBJECT_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }


    @PostMapping("/{companyId}/branches/{branchId}/admin/customers")
    fun addNewCustomerToCompanyByAdmin(@PathVariable companyId: Long,
                                       @PathVariable branchId: Long,
                                       @RequestBody externalCustomer: ExternalCustomer?)
            : ResponseEntity<ServerResponse<ExternalCustomer?>> {

        externalCustomer?.let {
            return customersServiceImpl.addCustomerToCompany(it, companyId, branchId, null)
        }

        return ResponseEntity(
                ServerResponse<ExternalCustomer?>(ResponseCode.CUSTOMER_OBJECT_NOT_VALID, null),
                HttpStatus.BAD_REQUEST)
    }


    @GetMapping("/{companyId}/customers")
    fun getCompanyCustomersList(@PathVariable companyId: Long): Set<ExternalCustomer> {
        return customersServiceImpl.getCompanyCustomers(companyId).map {
            it.toExCustomer()
        }.toSet()
    }
}