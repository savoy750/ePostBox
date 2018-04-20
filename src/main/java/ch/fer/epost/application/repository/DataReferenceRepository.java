package ch.fer.epost.application.repository;

import ch.fer.epost.application.domain.DataReference;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DataReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataReferenceRepository extends JpaRepository<DataReference, Long> {

}
