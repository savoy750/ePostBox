package ch.fer.epost.application.repository.search;

import ch.fer.epost.application.domain.DataReference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DataReference entity.
 */
public interface DataReferenceSearchRepository extends ElasticsearchRepository<DataReference, Long> {
}
