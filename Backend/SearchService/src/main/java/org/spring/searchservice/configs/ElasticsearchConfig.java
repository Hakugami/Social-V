package org.spring.searchservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;



@Configuration
@EnableElasticsearchRepositories(basePackages = "org.spring.searchservice.repositories")
public class ElasticsearchConfig extends ElasticsearchConfiguration {


	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
				.connectedTo("localhost:9200")
				.build();
	}
}