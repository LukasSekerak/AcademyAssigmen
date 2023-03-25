package sk.ness.academy.service;

import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleRepository;
import sk.ness.academy.projections.ArticlesWithoutComments;
import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public Article findByID(final Integer articleId) throws NullPointerException, ResourceNotFoundException {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    return articleRepository.findById(articleId).orElseThrow(
            () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists."));
  }


  @Override
  public void deleteByID(Integer articleId) {
    articleRepository.deleteByid(articleId);
  }

  @Override
  public List<ArticlesWithoutComments> findAll() {
    final List<ArticlesWithoutComments> listOfArticles = this.articleRepository.findAllProjecteBy();

    if (listOfArticles == null) {
      throw new NullPointerException("Articles not exist in database");
    }
	  return listOfArticles;
  }

  @Override
  public List<Article> searchArticle(String string) { return this.articleRepository.findByTextContainingOrTitleContainingOrAuthorContaining(string, string ,string); }

  @Override
  public void createArticle(final Article article) {
	  this.articleRepository.save(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    Gson gson = new Gson();

    Article[] articles = gson.fromJson(jsonArticles, Article[].class);

    for (Article article : articles) {
      this.articleRepository.save(article);
    }


  }

}
