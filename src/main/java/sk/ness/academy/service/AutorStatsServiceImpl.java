package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleRepository;
import sk.ness.academy.dto.AuthorStats;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AutorStatsServiceImpl implements AuthorStatsService {
    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public List<AuthorStats> authorsStats() { return this.articleRepository.authorsStats(); }
}
