package org.example.Hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

public class UsersDAO {
    private Session session;

    public UsersDAO(@NotNull Session session) {
        this.session = session;
    }

    public UsersDataSet get(@NotNull String login) {
        return session.get(UsersDataSet.class, login);
    }

    public UsersDataSet getUserByEmail(@NotNull String email){
        return session.get(UsersDataSet.class, email);
    }

    public String insertUser(String login, String password, String email) throws HibernateException {
        return (String) session.save(new UsersDataSet(login, password, email));
    }
}