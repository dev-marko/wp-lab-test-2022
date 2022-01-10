package mk.ukim.finki.wp.lab2022.service.impl;

import mk.ukim.finki.wp.lab2022.model.NewsCategory;
import mk.ukim.finki.wp.lab2022.model.exceptions.InvalidNewsCategoryIdException;
import mk.ukim.finki.wp.lab2022.repository.NewsCategoryRepository;
import mk.ukim.finki.wp.lab2022.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCategoryImpl implements NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    @Autowired
    public NewsCategoryImpl(NewsCategoryRepository newsCategoryRepository) {
        this.newsCategoryRepository = newsCategoryRepository;
    }

    @Override
    public NewsCategory findById(Long id) {
        return this.newsCategoryRepository.findById(id).orElseThrow(InvalidNewsCategoryIdException::new);
    }

    @Override
    public List<NewsCategory> listAll() {
        return this.newsCategoryRepository.findAll();
    }

    @Override
    public NewsCategory create(String name) {
        NewsCategory newNewsCategory = new NewsCategory();
        newNewsCategory.setName(name);
        return this.newsCategoryRepository.save(newNewsCategory);
    }
}
