package ch.fer.epost.application.repository.search;

import ch.fer.epost.application.domain.KeyReference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the KeyReference entity.
 */
public interface KeyReferenceSearchRepository extends ElasticsearchRepository<KeyReference, Long> {
}
