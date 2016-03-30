package com.github.aureliano.edocs.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.aureliano.edocs.common.persistence.IEntity;

public class Document implements IEntity<Document> {

	private Integer id;
	private Category category;
	private String description;
	private Date dueDate;
	private List<Attachment> attachments;
	
	public Document() {
		this.attachments = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public Document withId(Integer id) {
		this.id = id;
		return this;
	}

	public Category getCategory() {
		return category;
	}

	public Document withCategory(Category category) {
		this.category = category;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Document withDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Document withDueDate(Date dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public Document withAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
		return this;
	}
	
	public Document attach(Attachment attachment) {
		this.attachments.add(attachment);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Document other = (Document) obj;
		if (category != other.category)
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}