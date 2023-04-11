package sk.ness.academy.springbootHibernate.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sk.ness.academy.config.TestDataSourceConfig;
import sk.ness.academy.controller.BlogController;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.ControllerExceptionHandler;
import sk.ness.academy.exception.ResourceNotFoundException;
import sk.ness.academy.service.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(BlogController.class)
@ContextConfiguration(classes = { TestDataSourceConfig.class })
public class BlogControllerTest {
    @Mock
    private ArticleServiceImpl articleService;
    @Mock
    private CommentServiceImpl commentService;
    @Mock
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorStatsServiceImpl authorStatsService;
    private Article article;
    private List<Article> articles;
    private List<Comment> commentsOfArticle;
    @InjectMocks
    private BlogController blogController;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setup(){
        final Comment newComment1 = new Comment();
        final Comment newComment2 = new Comment();
       commentsOfArticle = new ArrayList<>();

        newComment1.setId(1);
        newComment1.setAuthor("Joseph Little");
        newComment1.setText("Text text text");
        commentsOfArticle.add(newComment1);

        newComment2.setId(2);
        newComment2.setAuthor("Joseph Little_2");
        newComment2.setText("Text text text 2");
        commentsOfArticle.add(newComment2);

        article = new Article();
        article.setId(1);
        article.setTitle("Title");
        article.setText("Text");
        article.setAuthor("Author");
        article.setComments(commentsOfArticle);

        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("Title2");
        article2.setText("Text2");
        article2.setAuthor("Author2");

        articles = Arrays.asList(article, article2);

        mockMvc = MockMvcBuilders.standaloneSetup(blogController).setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }

    @AfterEach
    void tearDown() {
        article = null;
    }

    @Test
    public void getAllArticles() throws Exception {

        Mockito.when(articleService.findAll()).thenReturn(articles);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Title")))
                .andExpect(jsonPath("$[0].text", Matchers.is("Text")))
                .andExpect(jsonPath("$[0].author", Matchers.is("Author")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].title", Matchers.is("Title2")))
                .andExpect(jsonPath("$[1].text", Matchers.is("Text2")))
                .andExpect(jsonPath("$[1].author", Matchers.is("Author2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchArticle() throws Exception {
        String searchText = "Text";
        Mockito.when(articleService.searchArticle(searchText)).thenReturn(articles);

        mockMvc.perform(get("/articles/search/{searchText}",searchText))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Title")))
                .andExpect(jsonPath("$[0].text", Matchers.is("Text")))
                .andExpect(jsonPath("$[0].author", Matchers.is("Author")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].title", Matchers.is("Title2")))
                .andExpect(jsonPath("$[1].text", Matchers.is("Text2")))
                .andExpect(jsonPath("$[1].author", Matchers.is("Author2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getArticle() throws Exception {

        Integer articleId = article.getId();

        Mockito.when(articleService.findByID(articleId)).thenReturn(article);

        mockMvc.perform(get("/articles/{articleId}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Title")))
                .andExpect(jsonPath("$.text", Matchers.is("Text")))
                .andExpect(jsonPath("$.author", Matchers.is("Author")))
                .andExpect(jsonPath("$.comments", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.comments[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.comments[0].text", Matchers.is("Text text text")))
                .andExpect(jsonPath("$.comments[0].author", Matchers.is("Joseph Little")))
                .andExpect(jsonPath("$.comments[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$.comments[1].text", Matchers.is("Text text text 2")))
                .andExpect(jsonPath("$.comments[1].author", Matchers.is("Joseph Little_2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturnNotFoundArticle() throws Exception {
        Integer articleId = 100;

        Mockito.when(articleService.findByID(articleId)).thenThrow(new ResourceNotFoundException("Article with id: " + articleId + " doesn't exists."));


        mockMvc.perform(get("/articles/{articleId}", articleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Article with id: " + articleId + " doesn't exists.", result.getResolvedException().getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteArticle() throws Exception {

        Integer articleId = article.getId();

        mockMvc.perform(delete("/articles/{articleId}", articleId))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(articleService).deleteByID(articleId);
    }

    @Test
    void shouldReturnNotFoundDeletedArticle() throws Exception {
        Integer articleId = 100;

        Mockito.doThrow(new ResourceNotFoundException("Article with id: " + articleId + " doesn't exists.")).when(articleService).deleteByID(articleId);


        mockMvc.perform(delete("/articles/{articleId}", articleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Article with id: " + articleId + " doesn't exists.", result.getResolvedException().getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addArticle() throws Exception {
        final Article newArticle = new Article();
        newArticle.setTitle("Article_3");
        newArticle.setText("Text new");
        newArticle.setAuthor("Author New");


        mockMvc.perform(
                MockMvcRequestBuilders.put("/articles")
                    .content(asJsonString(newArticle))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //update
        newArticle.setId(1);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/articles")
                                .content(asJsonString(newArticle))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getComment() throws Exception {
        Integer commentId = commentsOfArticle.get(0).getId();

        Mockito.when(commentService.findByID(commentId)).thenReturn(commentsOfArticle.get(0));

        mockMvc.perform(get("/comments/{commentId}",commentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.text", Matchers.is("Text text text")))
                .andExpect(jsonPath("$.author", Matchers.is("Joseph Little")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturnNotFoundComment() throws Exception {
        Integer commentId = 100;

        Mockito.when(commentService.findByID(commentId)).thenThrow(new ResourceNotFoundException("Comment with id: " + commentId + " doesn't exists."));


        mockMvc.perform(get("/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Comment with id: " + commentId + " doesn't exists.", result.getResolvedException().getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addComment() throws Exception {
        Integer articleId = articles.get(0).getId();

        Comment newComment = new Comment();
        newComment.setText("Text new");
        newComment.setAuthor("Comment Author");

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/articles/{articleId}/comments",articleId)
                                .content(asJsonString(newComment))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //update
        newComment.setId(1);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/articles/{articleId}/comments",articleId)
                                .content(asJsonString(newComment))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCommentsOfArticle() throws Exception {
        Integer articleId = articles.get(0).getId();

        Mockito.when(commentService.findAllByIDOfArticle(articleId)).thenReturn(commentsOfArticle);

        mockMvc.perform(get("/articles/{articleId}/comments",articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].text", Matchers.is("Text text text")))
                .andExpect(jsonPath("$[0].author", Matchers.is("Joseph Little")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].text", Matchers.is("Text text text 2")))
                .andExpect(jsonPath("$[1].author", Matchers.is("Joseph Little_2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldReturnNotFoundArticleWithComments() throws Exception {
        Integer articleId = 100;

        Mockito.when(commentService.findAllByIDOfArticle(articleId)).thenThrow(new ResourceNotFoundException("Article with id: " + articleId + " doesn't exists."));


        mockMvc.perform(get("/articles/{articleId}/comments", articleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Article with id: " + articleId + " doesn't exists.", result.getResolvedException().getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteComment() throws Exception {
        Integer commentId = commentsOfArticle.get(0).getId();

        mockMvc.perform(delete("/comments/{articleId}",commentId))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(commentService).deleteByID(1);
    }

    @Test
    void shouldReturnNotFoundDeletedComment() throws Exception {
        Integer commentId = 100;

        Mockito.doThrow(new ResourceNotFoundException("Comment with id: " + commentId + " doesn't exists.")).when(commentService).deleteByID(commentId);

        mockMvc.perform(delete("/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Comment with id: " + commentId + " doesn't exists.", result.getResolvedException().getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllAuthors() throws Exception {

        Author author1 = new Author();
        Author author2 = new Author();
        author1.setName("Author");
        author2.setName("Author2");

       List<Author> authors = Arrays.asList(author1, author2);

        Mockito.when(authorService.findAll()).thenReturn(authors);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Author")))
                .andExpect(jsonPath("$[1].name", Matchers.is("Author2")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void authorStats() throws Exception {

        final AuthorStats as1 = new AuthorStats();
        as1.setAuthorName("Author");
        as1.setArticleCount(1);

        final AuthorStats as2 = new AuthorStats();
        as2.setAuthorName("Author2");
        as2.setArticleCount(1);

        List<AuthorStats> authorStats = Arrays.asList(as1, as2);

        Mockito.when(authorStatsService.authorsStats()).thenReturn(authorStats);

        mockMvc.perform(get("/authors/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].authorName", Matchers.is("Author")))
                .andExpect(jsonPath("$[0].articleCount", Matchers.is(1)))
                .andExpect(jsonPath("$[1].authorName", Matchers.is("Author2")))
                .andExpect(jsonPath("$[1].articleCount", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
