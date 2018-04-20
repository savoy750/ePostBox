package ch.fer.epost.application.repository.search;

import ch.fer.epost.application.domain.RejectedRegistration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RejectedRegistration entity.
 */
public interface RejectedRegistrationSearchRepository extends ElasticsearchRepository<RejectedRegistration, Long> {
}
