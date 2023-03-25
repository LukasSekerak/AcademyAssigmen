package sk.ness.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.service.ArticleService;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      context.registerShutdownHook();


      final ArticleService articleService = context.getBean(ArticleService.class);

      // Load file with articles and ingest
      try {
        String s = new String(Files.readAllBytes(Paths.get("articles_to_ingest.txt")));
        articleService.ingestArticles(s);
        System.out.print("Articles imported succesfully! \n \n");
      } catch (Exception e) {

      }
    }
  }
}
