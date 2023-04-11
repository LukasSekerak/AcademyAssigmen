package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

  @Resource
  private AuthorDAO authorDAO;

  @Override
  public List<Author> findAll() {

    final List<Author> listOfAuthors = this.authorDAO.findAll();

    if (listOfAuthors == null) {
      throw new NullPointerException("No authors in database.");
    }
    return listOfAuthors;
  }

}
