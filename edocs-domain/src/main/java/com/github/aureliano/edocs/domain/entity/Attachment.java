package com.github.aureliano.edocs.domain.entity;

import java.util.Date;

public class Attachment {

	private Integer id;
	private String name;
	private Date uploadTime;
	
	public Attachment() {}

	public Integer getId() {
		return id;
	}

	public Attachment withId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Attachment withName(String name) {
		this.name = name;
		return this;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public Attachment withUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
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