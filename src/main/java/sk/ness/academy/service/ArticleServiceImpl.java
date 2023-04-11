package sk.ness.academy.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Resource
  private CommentDAO commentDAO;

  @Override
  public Article findByID(final Integer articleId) throws NullPointerException, ResourceNotFoundException {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    Article a = articleDAO.findByID(articleId).orElseThrow(
            () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists."));

    List<Comment> listOfComments = new ArrayList<Comment>();

    if (!commentDAO.findAllByIDOfArticle(articleId).isEmpty())
      listOfComments = commentDAO.findAllByIDOfArticle(articleId);

    a.setComments(listOfComments);

    return a;
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
  public List<Article> searchArticle(String string) {
    List<Article> articles = this.articleDAO.searchArticle(string);
    for (Article article: articles) {
      article.setComments(commentDAO.findAllByIDOfArticle(article.getId()));
    }
    return articles;
  }

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
