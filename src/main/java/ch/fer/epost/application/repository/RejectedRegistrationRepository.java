package ch.fer.epost.application.repository;

import ch.fer.epost.application.domain.RejectedRegistration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RejectedRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RejectedRegistrationRepository extends JpaRepository<RejectedRegistration, Long> {

}
