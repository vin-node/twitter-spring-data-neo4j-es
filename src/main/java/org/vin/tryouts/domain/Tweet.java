package org.vin.tryouts.domain;

import java.util.ArrayList;
import java.util.Date;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	private String text;
	private Date createdAt = new Date();
	private String languageCode;
	@Relationship(type = "Author", direction = "OUTGOING")
	private Profile author;

	@Relationship(type = "Mention", direction = "OUTGOING")
	private ArrayList<Profile> mentionedInProfile = new ArrayList<Profile>();

	@Relationship(type = "Tag", direction = "OUTGOING")
	private ArrayList<Keyword> tags = new ArrayList<Keyword>();

	public Tweet() {

	}

	public Profile getAuthor() {
		return author;
	}

	public void setAuthor(Profile author) {
		this.author = author;
	}

	public ArrayList<Profile> getMentionedInProfile() {
		return mentionedInProfile;
	}

	public void setMentionedInProfile(ArrayList<Profile> mentionedInProfile) {
		this.mentionedInProfile = mentionedInProfile;
	}

	public ArrayList<Keyword> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Keyword> tags) {
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
}
