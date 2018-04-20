package ch.fer.epost.application.repository;

import ch.fer.epost.application.domain.KeyReference;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KeyReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyReferenceRepository extends JpaRepository<KeyReference, Long> {

}
