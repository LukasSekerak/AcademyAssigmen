package sk.ness.academy.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleRepository;
import sk.ness.academy.dao.CommentRepository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;
import sk.ness.academy.projections.ArticlesWithoutComments;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CommentRepository commentRepository;


  @Override
  public Article findById(final Integer articleId) throws NullPointerException, ResourceNotFoundException {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists."));
    if(commentRepository.findAllByArticleId(articleId).isPresent())
      article.setComments(commentRepository.findAllByArticleId(articleId).get());
    return article;
  }


  @Override
  public void deleteById(Integer articleId) {
    if (articleId == null) {
      throw new NullPointerException("Request param can't be null.");
    }

    if (articleRepository.existsById(articleId)) {
      articleRepository.deleteByid(articleId);
    } else {
      throw new ResourceNotFoundException("Article with id: " + Integer.valueOf(articleId) + " doesn't exists.");
    }

  }

  @Override
  public List<ArticlesWithoutComments> findAll() {
    final List<ArticlesWithoutComments> listOfArticles = this.articleRepository.findAllProjectedBy();

    if (listOfArticles == null) {
      throw new NullPointerException("No articles exist in database.");
    }
	  return listOfArticles;
  }

  @Override
  public List<Article> searchArticle(String string) {
    List<Article> articles = this.articleRepository.findByTextContainingOrTitleContainingOrAuthorContaining(string, string ,string);
    for (Article article: articles) {
      article.setComments(commentRepository.findAllByArticleId(article.getId()).get());
    }
    return articles;
  }

  @Override
  public void createArticle(final Article article) { this.articleRepository.save(article); }

  @Override
  public void ingestArticles(final String jsonArticles) {
    Gson gson = new Gson();

    Article[] articles = gson.fromJson(jsonArticles, Article[].class);

    for (Article article : articles) {
      this.articleRepository.save(article);
    }


  }

}
