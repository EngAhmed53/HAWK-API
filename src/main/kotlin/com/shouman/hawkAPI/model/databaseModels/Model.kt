package com.shouman.hawkAPI.model.databaseModels

import com.fasterxml.jackson.annotation.JsonIgnore
import com.shouman.hawkAPI.model.Audit
import com.shouman.hawkAPI.model.externalModels.ExternalCustomer
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

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "com_id", referencedColumnName = "id")
        var company: Company?,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "sales_id", referencedColumnName = "id")
        var salesman: Salesman?

) : Audit() {
    override fun hashCode(): Int {
        return id.hashCode() + firebaseUID.hashCode() + email.hashCode()
    }
}

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

        @Column(name = "img_url", nullable = true, updatable = true)
        var imgURL: String?,

        @OneToOne(mappedBy = "company")
        @JsonIgnore
        var user: User?,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "company")
        @JsonIgnore
        var branchesSet: MutableSet<Branch> = HashSet(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "company")
        @JsonIgnore
        var salesmenSet: MutableSet<Salesman> = HashSet(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "company")
        @JsonIgnore
        var customersSet: MutableSet<Customer> = HashSet()

) {
    override fun hashCode(): Int {
        return id.hashCode() +
                ownerName.hashCode() +
                ownerPhoneNumber.hashCode() +
                companyName.hashCode()
    }
}


@Entity
@Table(name = "salesmen")
data class Salesman(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "salesman_name", nullable = false, updatable = true)
        val salesmanName: String,

        @Column(name = "salesman_phone_number", nullable = false, updatable = true)
        val salesmanPhoneNumber: String,

        @Column(name = "img_url", nullable = true, updatable = true)
        var imgURL: String?,

        @Column(name = "salesman_status", nullable = false, updatable = true)
        @Enumerated(EnumType.STRING)
        val salesmanStatus: SalesmanStatus = SalesmanStatus.ENABLED,

        @OneToOne(mappedBy = "salesman")
        @JsonIgnore
        var user: User?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "branch_id", nullable = false)
        @JsonIgnore
        var branch: Branch,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id", nullable = false)
        @JsonIgnore
        var company: Company,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "salesman")
        @JsonIgnore
        var customersSet: MutableSet<Customer> = HashSet()

) {
    override fun hashCode(): Int {
        return id.hashCode() +
                salesmanName.hashCode() +
                salesmanPhoneNumber.hashCode()
    }
}


@Entity
@Table(name = "branches")
data class Branch(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "branch_name", nullable = false, updatable = true)
        var branchName: String,

        @Column(name = "branch_country", nullable = false, updatable = true)
        var country: String,

        @Column(name = "branch_city", nullable = false, updatable = true)
        var city: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id", nullable = false)
        @JsonIgnore
        var company: Company?,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "branch")
        var salesmenSet: MutableSet<Salesman> = HashSet(),

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "branch")
        @JsonIgnore
        var customersSet: MutableSet<Customer> = HashSet()
)


@Entity
@Table(name = "customers")
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "create_at", nullable = false, updatable = false)
        val createTime: Long,

        @Column(name = "created_by", nullable = false, updatable = false)
        val createdBy: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id", nullable = false)
        @JsonIgnore
        val company: Company,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "branch_id", nullable = false)
        @JsonIgnore
        val branch: Branch,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "salesman_id", nullable = true, updatable = true)
        @JsonIgnore
        val salesman: Salesman?,

        @Column(name = "customer_name", nullable = false, updatable = true)
        val customerName: String,

        @Column(name = "super_visor_name", nullable = false, updatable = true)
        val superVisor: String,

        @Column(name = "phone_num", nullable = false, updatable = true)
        val phoneNum: String,

        @Column(name = "email", nullable = true, updatable = true)
        val email: String,

        @Column(name = "latitude", nullable = false, updatable = true)
        val latitude: Double,

        @Column(name = "longitude", nullable = false, updatable = true)
        val longitude: Double,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "customer")
        val visitsSet: MutableSet<Visit> = HashSet()
) {
    override fun hashCode(): Int {
        return customerName.hashCode() +
                phoneNum.hashCode() +
                latitude.hashCode() +
                longitude.hashCode() +
                email.hashCode()
    }
}

fun Customer.toExCustomer():ExternalCustomer {
        return ExternalCustomer(
                id,
                createTime, createdBy,
                company.id, branch.id, salesman?.id,
                customerName, superVisor,
                phoneNum, email,
                latitude, longitude,
                visitsSet
        )
}


@Entity()
@Table(name = "visits")
data class Visit(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = "create_at", nullable = false, updatable = false)
        val createTime: Long,

        @Column(name = "invoice_num", nullable = true, updatable = true)
        val invoiceNum: Long?,

        @Column(name = "invoice_balance", nullable = true, updatable = true)
        val invoiceBalance: Int?,

        @Column(name = "invoice_cash", nullable = true, updatable = true)
        val invoiceCash: Int?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", nullable = false)
        @JsonIgnore
        var customer: Customer?
) {
    override fun hashCode(): Int {
        return createTime.hashCode() +
                id.hashCode()
    }
}
