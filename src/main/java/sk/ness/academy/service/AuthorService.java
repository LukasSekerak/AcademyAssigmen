package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.dto.Author;

public interface AuthorService {

	  /** Returns all available {@link Author}s */
	  List<Author> findAll();

	}
