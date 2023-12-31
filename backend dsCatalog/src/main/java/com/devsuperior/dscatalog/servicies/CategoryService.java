package com.devsuperior.dscatalog.servicies;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.servicies.exceptions.DatabaseException;
import com.devsuperior.dscatalog.servicies.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<CategoryDto> findAllPaged(Pageable pageAble) {
		Page<Category> list = categoryRepository.findAll(pageAble);
		return list.map(x -> new CategoryDto(x));

	}

	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new CategoryDto(entity);

	}

	@Transactional
	public CategoryDto insert(CategoryDto dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDto(entity);
	}

	@Transactional
	public CategoryDto update(Long id, CategoryDto dto) {
		try {
			Category entity = categoryRepository.getOne(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDto(entity);
		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("id not found" + id);

		}
	}

	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		}

		catch (EmptyResultDataAccessException e) {

			throw new ResourceNotFoundException("id not found" + id);
		}

		catch (DataIntegrityViolationException e) {

			throw new DatabaseException("Integrity violation");

		}
	}

}
