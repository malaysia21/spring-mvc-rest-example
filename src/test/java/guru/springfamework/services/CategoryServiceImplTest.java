package guru.springfamework.services;

import guru.springfamework.api.model.CategoryDTO;
import guru.springfamework.api.mapper.CategoryMapper;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest  {
    public static final Long ID = 2L;
    public static final String NAME = "JOY";


    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() throws Exception {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> allCategories = categoryService.getAllCategories();

        //then
        assertEquals(3, allCategories.size());


    }

    @Test
    public void getCategoryByName() throws Exception {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);
        when(categoryRepository.findByName(NAME)).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        assertEquals(NAME, categoryDTO.getName());
        assertEquals(ID, categoryDTO.getId());


    }

}