package sk.ness.academy.service;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import org.apache.el.parser.Node;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) throws NullPointerException, ResourceNotFoundException {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    return articleDAO.findByID(articleId).orElseThrow(
            () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists."));
  }


  @Override
  public void deleteByID(Integer articleId) {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    Article article = articleDAO.findByID(articleId).orElseThrow(
            () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists."));

    articleDAO.deleteByID(articleId);
  }

  @Override
  public List<Article> findAll() {
    final List<Article> listOfArticles = this.articleDAO.findAll();

    if (listOfArticles == null) {
      throw new NullPointerException("Articles doesn't exist in database");
    }
	  return listOfArticles;
  }

  @Override
  public List<Article> searchArticle(String string) { return this.articleDAO.searchArticle(string); }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    Gson gson = new Gson();

    Article[] articles = gson.fromJson(jsonArticles, Article[].class);

    for(Article article : articles) {
      this.articleDAO.persist(article);
    }
  }

}
