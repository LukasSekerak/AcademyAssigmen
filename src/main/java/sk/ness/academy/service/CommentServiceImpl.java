package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.CommentRepository;
import sk.ness.academy.domain.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void deleteByID(Integer commentId) {
        this.commentRepository.deleteByid(commentId);
    }

    @Override
    public void createComment(Comment comment) { this.commentRepository.save(comment); }

    @Override
    public List<Comment> findAllCommentsByArticleId(Integer idArticle) { return this.commentRepository.findAllByArticleId(idArticle); }

}
