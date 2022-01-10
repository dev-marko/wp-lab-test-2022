package mk.ukim.finki.wp.lab2022.service.impl;

import mk.ukim.finki.wp.lab2022.model.News;
import mk.ukim.finki.wp.lab2022.model.NewsCategory;
import mk.ukim.finki.wp.lab2022.model.NewsType;
import mk.ukim.finki.wp.lab2022.model.exceptions.InvalidNewsCategoryIdException;
import mk.ukim.finki.wp.lab2022.model.exceptions.InvalidNewsIdException;
import mk.ukim.finki.wp.lab2022.repository.NewsCategoryRepository;
import mk.ukim.finki.wp.lab2022.repository.NewsRepository;
import mk.ukim.finki.wp.lab2022.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsCategoryRepository newsCategoryRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, NewsCategoryRepository newsCategoryRepository) {
        this.newsRepository = newsRepository;
        this.newsCategoryRepository = newsCategoryRepository;
    }

    @Override
    public List<News> listAllNews() {
        return this.newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return this.newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
    }

    @Override
    public News create(String name, String description, Double price, NewsType type, Long category) {
        NewsCategory newsCategory = this.newsCategoryRepository.findById(category).orElseThrow(InvalidNewsCategoryIdException::new);

        News newNews = new News();
        newNews.setName(name);
        newNews.setDescription(description);
        newNews.setPrice(price);
        newNews.setLikes(0);
        newNews.setType(type);
        newNews.setCategory(newsCategory);

        return this.newsRepository.save(newNews);
    }

    @Override
    public News update(Long id, String name, String description, Double price, NewsType type, Long category) {

        NewsCategory updateNewsCategory = this.newsCategoryRepository.findById(category).orElseThrow(InvalidNewsCategoryIdException::new);

        News updateNews = this.findById(id);

        updateNews.setName(name);
        updateNews.setDescription(description);
        updateNews.setPrice(price);
        updateNews.setLikes(0);
        updateNews.setType(type);
        updateNews.setCategory(updateNewsCategory);

        return this.newsRepository.save(updateNews);
    }

    @Override
    public News delete(Long id) {
        News news = this.findById(id);
        this.newsRepository.delete(news);
        return news;
    }

    @Override
    public News like(Long id) {
        News news = this.findById(id);
        news.setLikes(news.getLikes() + 1);
        return this.newsRepository.save(news);
    }

    @Override
    public List<News> listNewsWithPriceLessThanAndType(Double price, NewsType type) {
        if (price == null && type == null) {
            return this.newsRepository.findAll();
        }

        if (price != null && type != null) {
            return this.newsRepository.findNewsByPriceLessThanAndType(price, type);
        } else if (price == null) {
            return this.newsRepository.findNewsByType(type);
        } else {
            return this.newsRepository.findNewsByPriceLessThan(price);
        }

    }
}
