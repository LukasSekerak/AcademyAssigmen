package sk.ness.academy.dao;

import java.util.List;
import java.util.Optional;

import sk.ness.academy.domain.Article;
import sk.ness.academy.exception.ResourceNotFoundException;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Optional<Article> findByID(Integer articleId) throws ResourceNotFoundException, NullPointerException;

	  /** Delete {@link Article} with provided ID */
	  void deleteByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll() throws NullPointerException;

	  /** Returns all available {@link Article}s where author, title or text contains the searched text.*/
	  List<Article> searchArticle(String string);

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);
	}
