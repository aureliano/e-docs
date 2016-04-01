package com.github.aureliano.edocs.domain.entity;

import java.util.Date;

import com.github.aureliano.edocs.annotation.validation.NotEmpty;
import com.github.aureliano.edocs.annotation.validation.NotNull;
import com.github.aureliano.edocs.annotation.validation.Size;
import com.github.aureliano.edocs.common.persistence.IEntity;

public class Attachment implements IEntity {

	private Integer id;
	private String name;
	private Date uploadTime;
	private Document document;
	
	public Attachment() {}

	public Integer getId() {
		return id;
	}

	public Attachment withId(Integer id) {
		this.id = id;
		return this;
	}

	@NotEmpty
	@Size(min = 5, max = 250)
	public String getName() {
		return name;
	}

	public Attachment withName(String name) {
		this.name = name;
		return this;
	}

	@NotNull
	public Date getUploadTime() {
		return uploadTime;
	}

	public Attachment withUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
		return this;
	}

	@NotNull
	public Document getDocument() {
		return document;
	}
	
	public Attachment withDocument(Document document) {
		this.document = document;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uploadTime == null) ? 0 : uploadTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attachment other = (Attachment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uploadTime == null) {
			if (other.uploadTime != null)
				return false;
		} else if (!uploadTime.equals(other.uploadTime))
			return false;
		return true;
	}
}