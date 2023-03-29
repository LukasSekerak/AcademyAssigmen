package sk.ness.academy.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Comment;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment save(Comment entity);

    Optional<Comment> findById(Integer primaryKey);

    List<Comment> findAll();

    @Query(value = "select * from comments where article_id = :id", nativeQuery = true)
    Optional<List<Comment>> findAllByArticleId(@Param("id") Integer idArticle);

    void deleteByid(Integer primaryKey);

}
