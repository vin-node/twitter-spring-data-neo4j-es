package org.vin.tryouts.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.vin.tryouts.domain.Person;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

	Person findByName(String name);

}