package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dao.AuthorStatsDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AutorStatsServiceImpl implements AuthorStatsService {
    @Resource
    private AuthorStatsDAO autorStatsDAO;
    @Override
    public List<AuthorStats> authorsStats() { return this.autorStatsDAO.authorsStats(); }
}
