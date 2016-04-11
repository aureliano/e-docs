package com.github.aureliano.edocs.app.model;

import com.github.aureliano.edocs.common.helper.StringHelper;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class ComboBoxItemModel<T> {

	private String internationalizationKey;
	private T value;
	
	public ComboBoxItemModel() {
		this(null);
	}
	
	public ComboBoxItemModel(String internationalizationKey) {
		this(internationalizationKey, null);
	}
	
	public ComboBoxItemModel(String internationalizationKey, T value) {
		this.internationalizationKey = internationalizationKey;
		this.value = value;
	}

	public String getInternationalizationKey() {
		return internationalizationKey;
	}

	public void setInternationalizationKey(String internationalizationKey) {
		this.internationalizationKey = internationalizationKey;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if (this.internationalizationKey == null) {
			return StringHelper.parse(this.value);
		}
		
		String label = EdocsLocale.instance().getMessage(this.internationalizationKey);
		return (label == null) ? StringHelper.parse(this.value) : label;
	}
}