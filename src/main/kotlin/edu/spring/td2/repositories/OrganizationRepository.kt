package edu.spring.td2.repositories

import edu.spring.td2.entities.Organization
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationRepository: JpaRepository<Organization, Int> {
}