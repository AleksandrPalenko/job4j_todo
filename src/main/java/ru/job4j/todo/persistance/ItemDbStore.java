package ru.job4j.todo.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.function.Function;
import java.util.List;


@Repository
public class ItemDbStore {

    private final SessionFactory sf;

    public ItemDbStore(SessionFactory sf) {
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

    public void deleteFrom() {
        this.tx(session -> session.createQuery("TRUNCATE TABLE items RESTART IDENTITY"));
    }

    public Item add(Item item) {
        this.tx(session -> session.save(item));
        return item;
    }

    public boolean delete(int id) {
        return this.tx(session -> {
                    final Query query = session.createQuery("DELETE FROM Item i WHERE i.id = :fId ");
                    query.setParameter("fId", id).executeUpdate();
                    return true;
                }
        );
    }

    public void updateByIdToTrue(int id) {
        this.tx(session ->
                session.createQuery("UPDATE Item i SET i.done = :newDone WHERE i.id = :fId ")
                        .setParameter("newDone", true)
                        .setParameter("fId", id)
                        .executeUpdate()
        );
    }

    public void updateByIdToFalse(int id) {
        this.tx(session ->
                session.createQuery("UPDATE Item i SET i.done = :newDone WHERE i.id = :fId ")
                        .setParameter("newDone", false)
                        .setParameter("fId", id)
                        .executeUpdate()
        );
    }

    public List<Item> findAllItems() {
        return this.tx(session ->
                session.createQuery("select distinct i from Item i left join fetch i.categories ").list()
        );
    }

    public List<Item> findTrueItems() {
        return this.tx(session ->
                session.createQuery("select distinct i from Item i join fetch i.categories where i.done = false").list()
        );
    }

    public List<Item> findFalseItems() {
        return this.tx(session ->
                session.createQuery("select distinct i from Item i join fetch i.categories where i.done = done ").list()
        );
    }

    public List<Item> findByName(String key) {
        return this.tx(session ->
                session.createQuery(key, Item.class).list()
        );
    }

    public Item findById(int id) {
        return this.tx(session ->
                session.get(Item.class, id)
        );
    }

    public void update(Item item) {
        this.tx(
                session -> {
                    session.update(item);
                    return new Object();
                }
        );
    }

    public Item findByIdWithCategories(int id) {
        return (Item) this.tx(session -> session.createQuery("select distinct i from Item i left join fetch i.categories where i.id = :fId")
                .setParameter("fId", id)
                .uniqueResult());
    }

}
