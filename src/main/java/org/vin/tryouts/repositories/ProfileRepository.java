package org.vin.tryouts.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vin.tryouts.domain.Profile;

@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
public interface ProfileRepository extends Neo4jRepository<Profile, Long> {
	Profile findByScreenName(String screenName);
}
