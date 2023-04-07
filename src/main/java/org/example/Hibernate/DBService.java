package org.example.Hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DBService {
    private static final String url = "jdbc:mysql://localhost:3306/servlet";
    private static final String user = "root";
    private static final String password = "root";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String showSql = "true";
    private static final String dialect = "org.hibernate.dialect.MySQL5Dialect";
    private static final String hdm2ddl = "update";

    private final Configuration configuration;

    private final SessionFactory sessionFactory;

    public DBService() {
        configuration = getConfiguration();
        sessionFactory = createSessionFactory();
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.connection.driver_class", driver)
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", user)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.dialect", dialect)
                .setProperty("hibernate.show_sql", showSql)
                .setProperty("hibernate.hbm2ddl.auto", hdm2ddl)
                .setProperty("hibernate.current_session_context_class", "thread");

        return configuration;
    }

    private SessionFactory createSessionFactory(){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public boolean addUser(@NotNull String login, @NotNull String password, @NotNull String email) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.insertUser(login, password, email);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public UsersDataSet getUser(@NotNull String login) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(login);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public boolean emailIsUnique(@NotNull String email) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.getUserByEmail(email);
            session.close();
            return dataSet == null;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public boolean loginIsUnique(@NotNull String login) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(login);
            session.close();
            return dataSet == null;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }
}
