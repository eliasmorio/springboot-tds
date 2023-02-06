package edu.spring.td2.repositories

import edu.spring.td2.entities.Organization
import org.springframework.data.repository.CrudRepository

interface OrganizationRepository: CrudRepository<Organization, Int> {
    abstract fun findByNameContainingOrDomainContainingOrAliasesContaining(query: String, query1: String, query2: String): List<Organization>?
}