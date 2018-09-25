package org.vin.tryouts.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Keyword {

	@Id
	@GeneratedValue
	private Long id;

	@Index(unique = true)
	private String word;
	private Date createdAt = new Date();

	@Relationship(type = "CONNECTED", direction = "OUTGOING")
	private Set<Keyword> connected = new HashSet<>();

	public Keyword() {

	}

	public Keyword(String word) {
		this.word = word;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
