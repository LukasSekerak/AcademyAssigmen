This application is a simple blog API. It offers capability to add Articles, list them by various criteria.

// ~~ Installation

Simply import as a maven project into IDE.

// ~~ Usage

There are 2 runners
	- sk.ness.academy.AssignmentApplication which runs the whole applications including web server
	- sk.ness.academy.ArticleIngester which runs just the necessary parts of the system 
	
After starting the whole application using ApplicationRunner, web server is accessible on localhost:8080

API:

GET http://localhost:8080/articles - returns list of all articles

GET http://localhost:8080/articles/{articleId} - returns single article with provided article ID

PUT http://localhost:8080/articles - creates a new article. 
Example: Content-Type header with value application/json and request body in JSON format as following: 
{
  "author": "Author Name",
  "text": "Article text",
  "title": "Article Title"
}

GET http://localhost:8080/authors - returns list of all authors

// ~~ Tips
1. There are lots of tools like Postman (Chrome) that can be used to http communication. Or try http://localhost:8080/swagger-ui.html :)
2. DB is automatically generated from domain entities (there is no need to modify mydb.script e.g. when introducing new tables, columns, etc.)
3. When a service is @Transactional it handles the DB transactions for you
4. Google