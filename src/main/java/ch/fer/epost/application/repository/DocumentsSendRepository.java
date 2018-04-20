package ch.fer.epost.application.repository;

import ch.fer.epost.application.domain.DocumentsSend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DocumentsSend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsSendRepository extends JpaRepository<DocumentsSend, Long> {

}
