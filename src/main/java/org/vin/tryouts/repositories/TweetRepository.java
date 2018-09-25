package org.vin.tryouts.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vin.tryouts.domain.Tweet;

@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetRepository extends Neo4jRepository<Tweet, Long> {
}
