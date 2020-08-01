package com.shouman.hawkAPI.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

enum class UserType {
    NULL, COMPANY, SALESMAN
}


enum class SalesmanStatus {
    ENABLED, DISABLED
}

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "firebase_uid", nullable = false, updatable = false, unique = true)
        val firebaseUID: String,

        @Column(name = "user_email", nullable = false, updatable = false, unique = true)
        val email: String,

        @Enumerated(EnumType.STRING)
        @Column(name = "user_type", nullable = false)
        var type: UserType = UserType.NULL,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "com_id")
        var company: Company?,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "sales_id")
        var salesman: Salesman?

) : Audit()

@Entity
@Table(name = "companies")
data class Company(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "owner_name", nullable = true, updatable = true)
        var ownerName: String,

        @Column(name = "owner_phone_number", nullable = true, updatable = true)
        var ownerPhoneNumber: String,

        @Column(name = "company_name", nullable = true, updatable = true)
        var companyName: String,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "company")
        @JsonIgnore
        var user: User?
)


@Entity
@Table(name = "salesmen")
data class Salesman(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "salesman_name", nullable = true, updatable = true)
        val salesmanName: String?,

        @Column(name = "salesman_phone_number", nullable = true, updatable = true)
        val salesmanPhoneNumber: String?,

        @Column(name = "branch_id", nullable = false, updatable = true)
        val branchId: String?,

        @Column(name = "company_id", nullable = false, updatable = true)
        val companyId: String?,

        @Column(name = "salesman_status", nullable = false, updatable = true)
        @Enumerated(EnumType.STRING)
        val salesmanStatus: SalesmanStatus = SalesmanStatus.ENABLED,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "salesman")
        @JsonIgnore
        var user: User?
)