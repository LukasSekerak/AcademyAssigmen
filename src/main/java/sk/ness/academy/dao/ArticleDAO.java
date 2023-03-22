package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Delete {@link Article} with provided ID */
	  void deleteByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Returns all available {@link Article}s where author, title or text contains the searched text.*/
	  List<Article> searchArticle(String string);

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);
	}
