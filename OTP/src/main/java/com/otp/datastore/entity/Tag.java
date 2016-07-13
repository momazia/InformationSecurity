package com.otp.datastore.entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "TAG")
@Cache
public class Tag {

    @Id
    private Long id;
    @Index
    private String type;

    public Tag() {
    }

    public Key<Tag> getKey() {
	return Key.create(Tag.class, getId());
    }

    public Tag(Long id, String type) {
	super();
	this.id = id;
	this.type = type;
    }

    public Tag(String tag) {
	this.type = tag;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
