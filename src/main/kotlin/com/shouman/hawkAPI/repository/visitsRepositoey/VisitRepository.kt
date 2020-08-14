package com.shouman.hawkAPI.repository.visitsRepositoey

import com.shouman.hawkAPI.model.databaseModels.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VisitsRepo: JpaRepository<Visit, Long>