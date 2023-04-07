package sk.ness.academy.config;

import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TestDataSourceConfig {
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        final org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true");


        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "sk.ness.academy.domain" });
        sessionFactory.setHibernateProperties(configuration.getProperties());

        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new SimpleDriverDataSource(new JDBCDriver(), "jdbc:hsqldb:file:testdb;shutdown=true", "sa", "");
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(final SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
