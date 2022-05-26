package ru.job4j.todo.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserDbStore {

    private final SessionFactory sf;

    public UserDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public Optional<User> add(User user) {
        this.tx(session -> {
            session.save(user);
            return Optional.ofNullable(user);
        });
        return Optional.empty();
    }

    public User findById(int id) {
        return this.tx(session ->
                session.get(User.class, id)
        );
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return this.tx(session ->
                session.createQuery("from User u where u.email = :newEmail AND u.password = :newPassword")
                        .setParameter("newEmail", email)
                        .setParameter("newPassword", password)
                        .uniqueResultOptional()
                );
    }

    public Optional<User> findUserByEmail(String email) {
        return this.tx(session ->
                session.createQuery("from User u where u.email = :newEmail ")
                        .setParameter("newEmail", email)
                        .uniqueResultOptional()
        );

    }

    public List<User> findAllUsers() {
        return this.tx(session ->
                session.createQuery("from User").list()
        );
    }
}
