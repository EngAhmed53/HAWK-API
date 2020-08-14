package com.shouman.hawkAPI.repository.companiesRepository

import com.shouman.hawkAPI.model.databaseModels.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompaniesRepo : JpaRepository<Company, Long>