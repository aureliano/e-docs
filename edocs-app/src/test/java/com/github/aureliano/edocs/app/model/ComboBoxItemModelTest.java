package com.github.aureliano.edocs.app.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.domain.entity.Category;

public class ComboBoxItemModelTest {

	@Test
	public void testT() {
		ComboBoxItemModel<Category> item = new ComboBoxItemModel<Category>() {};
		item.setValue(Category.CHECK);
		
		assertEquals("CHECK", item.toString());
		
		item.setInternationalizationKey("domain.enum.category.check");
		assertEquals("Check", item.toString());
	}
}