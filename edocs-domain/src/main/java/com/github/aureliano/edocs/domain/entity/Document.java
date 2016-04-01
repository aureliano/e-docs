package com.github.aureliano.edocs.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.aureliano.edocs.annotation.validation.AssertTrue;
import com.github.aureliano.edocs.annotation.validation.NotEmpty;
import com.github.aureliano.edocs.annotation.validation.NotNull;
import com.github.aureliano.edocs.annotation.validation.Size;
import com.github.aureliano.edocs.common.persistence.IEntity;

public class Document implements IEntity {

	private Integer id;
	private Category category;
	private String description;
	private Date dueDate;
	private User owner;
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

	@NotNull
	public Category getCategory() {
		return category;
	}

	public Document withCategory(Category category) {
		this.category = category;
		return this;
	}

	@NotEmpty
	@Size(min = 5, max = 1000)
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
	
	public User getOwner() {
		return owner;
	}
	
	public Document withOwner(User owner) {
		this.owner = owner;
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
	
	@AssertTrue
	public boolean hasAttachment() {
		return (this.attachments == null) ? false : !this.attachments.isEmpty();
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