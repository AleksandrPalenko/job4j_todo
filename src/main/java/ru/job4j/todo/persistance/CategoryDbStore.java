package ru.job4j.todo.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CategoryDbStore {

    private final SessionFactory sf;

    public CategoryDbStore(SessionFactory sf) {
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

    public Optional<Category> add(Category category) {
        this.tx(session -> {
            session.save(category);
            return Optional.ofNullable(category);
        });
        return Optional.empty();
    }

    public List<Category> findAll() {
        return this.tx(session ->
                session.createQuery("from Category").list()
        );
    }

    public Category findById(int id) {
        return this.tx(session ->
                session.get(Category.class, id));
    }

}
