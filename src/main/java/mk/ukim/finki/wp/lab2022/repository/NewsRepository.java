package mk.ukim.finki.wp.lab2022.repository;

import mk.ukim.finki.wp.lab2022.model.News;
import mk.ukim.finki.wp.lab2022.model.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    //List<News> findAllByPriceLessThanAndType(Double price, NewsType type);
    List<News> findNewsByPriceLessThanAndType(Double price, NewsType type);
    List<News> findNewsByType(NewsType type);
    List<News> findNewsByPriceLessThan(Double price);
}
