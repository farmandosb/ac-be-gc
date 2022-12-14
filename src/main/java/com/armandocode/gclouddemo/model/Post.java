package com.armandocode.gclouddemo.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
public class Post {
	@Id

	private Long id;

	private String title;

	private String content;

	private Instant createdOn;

	private Instant updateOn;

	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		System.out.println(content);
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}

	public Instant getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Instant updateOn) {
		this.updateOn = updateOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
