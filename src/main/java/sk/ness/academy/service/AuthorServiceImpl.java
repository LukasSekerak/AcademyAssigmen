package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleRepository;
import sk.ness.academy.dto.Author;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public List<Author> findAll() {
    return this.articleRepository.findAllAuthors();
  }

}
