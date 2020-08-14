package com.shouman.hawkAPI.model.externalModels

import com.shouman.hawkAPI.model.databaseModels.Visit

data class ExternalCustomer(

        val id: Long,

        val createTime: Long,

        val createdBy: String,

        val companyId: Long?,

        val branchId: Long?,

        val salesmanId: Long?,

        val customerName: String,

        val superVisor: String,

        val phoneNum: String,

        val email: String,

        val latitude: Double,

        val longitude: Double,

        val visitsSet: MutableSet<Visit> = HashSet()
) {
    override fun hashCode(): Int {
        return customerName.hashCode() +
                phoneNum.hashCode() +
                latitude.hashCode() +
                longitude.hashCode() +
                email.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExternalCustomer

        if (id != other.id) return false
        if (createTime != other.createTime) return false
        if (createdBy != other.createdBy) return false
        if (companyId != other.companyId) return false
        if (branchId != other.branchId) return false
        if (salesmanId != other.salesmanId) return false
        if (customerName != other.customerName) return false
        if (superVisor != other.superVisor) return false
        if (phoneNum != other.phoneNum) return false
        if (email != other.email) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (visitsSet != other.visitsSet) return false

        return true
    }
}
