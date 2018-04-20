package ch.fer.epost.application.repository.search;

import ch.fer.epost.application.domain.DocumentsSend;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentsSend entity.
 */
public interface DocumentsSendSearchRepository extends ElasticsearchRepository<DocumentsSend, Long> {
}
