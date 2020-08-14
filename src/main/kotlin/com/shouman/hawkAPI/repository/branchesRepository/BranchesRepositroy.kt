package com.shouman.hawkAPI.repository.branchesRepository

import com.shouman.hawkAPI.model.databaseModels.Branch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BranchesRepo: JpaRepository<Branch, Long> {

    @Query("SELECT * FROM branches WHERE company_id = :companyId", nativeQuery = true)
    fun getCompanyBranches(@Param("companyId") companyId:Long): MutableList<Branch>
}
