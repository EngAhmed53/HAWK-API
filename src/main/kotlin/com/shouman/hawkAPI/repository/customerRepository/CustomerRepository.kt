package com.shouman.hawkAPI.repository.customerRepository

import com.shouman.hawkAPI.model.databaseModels.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomersRepo: JpaRepository<Customer, Long> {
    @Query("SELECT * FROM customers WHERE company_id = :companyId", nativeQuery = true)
    fun getAllCompanyCustomers(companyId: Long): MutableSet<Customer>
}