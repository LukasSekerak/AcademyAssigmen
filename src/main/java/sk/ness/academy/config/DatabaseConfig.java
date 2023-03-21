package sk.ness.academy.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class DatabaseConfig {

  @Bean(name = "sessionFactory")
  public LocalSessionFactoryBean sessionFactory() {
    final org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration()
    .setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
    .setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
    .setProperty("hibernate.connection.pool_size", "1")
    .setProperty("hibernate.connection.autocommit", "true")
    .setProperty("hibernate.hbm2ddl.auto", "update")
    .setProperty("hibernate.show_sql", "false");

    final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan(new String[] { "sk.ness.academy.domain" });
    sessionFactory.setHibernateProperties(configuration.getProperties());

    return sessionFactory;
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    return new SimpleDriverDataSource(new JDBCDriver(), "jdbc:hsqldb:file:mydb;shutdown=true", "sa", "");
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(final SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }

}
