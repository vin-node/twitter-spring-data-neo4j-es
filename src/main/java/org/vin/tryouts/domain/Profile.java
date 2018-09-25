package org.vin.tryouts.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Profile {
	@Id
	@GeneratedValue
	private Long id;

	@Index(unique = true)
	private String screenName;

	public Profile() {
	}

	public Profile(String screenName) {
		this.screenName = screenName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}
