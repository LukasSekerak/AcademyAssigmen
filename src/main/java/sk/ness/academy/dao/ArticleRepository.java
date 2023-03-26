package sk.ness.academy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.projections.ArticlesWithoutComments;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

  Article save(Article entity);

  Optional<Article> findById(Integer primaryKey);

  List<ArticlesWithoutComments> findAllProjectedBy();

  List<Article> findByTextContainingOrTitleContainingOrAuthorContaining(String s1, String s2, String s3);
  void deleteByid(Integer primaryKey);

  @Query("select distinct new sk.ness.academy.dto.Author(u.author) from Article u")
  List<Author> findAllAuthors();

  @Query("select new sk.ness.academy.dto.AuthorStats(u.author, COUNT(*)) from Article as u GROUP BY u.author")
  List<AuthorStats> authorsStats();

}

