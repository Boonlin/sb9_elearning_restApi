package co.istad.elearningapi;

import co.istad.elearningapi.model.Category;
import co.istad.elearningapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ELearningApiTests {
/*	@Autowired
	CategoryRepository categoryRepository;*/

/*
	@Test
	void test_updateCategoryName() {
		String newName = "Data Science";
		categoryRepository.updateCategoryName(1, newName);
	}

	@Test
	void test_selectAllCategories() {
		List<Category> categories = categoryRepository.selectAllCategories();
		categories.forEach(category -> {
			System.out.println(category.getId());
			System.out.println(category.getName());
			System.out.println(category.getIsDeleted());
			System.out.println("-----------------------");
		});
	}

	@Test
	void test_selectCategoryNames() {
		List<String> categories = categoryRepository.selectCategoryNames();
		System.out.println(categories);
	}
	@Test
	void test_selectCategoryByNameAndIsDeleted() {
		List<Category> categories = categoryRepository.selectCategoryByNameAndIsDeleted("Fullstack", false);
		categories.forEach(category -> {
			System.out.println(category.getId());
			System.out.println(category.getName());
			System.out.println(category.getIsDeleted());
			System.out.println("-----------------------");
		});
	}
*/

}
