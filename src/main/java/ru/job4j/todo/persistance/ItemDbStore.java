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

    public void deleteFrom() {
        final Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("TRUNCATE TABLE items RESTART IDENTITY");
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("delete from Item i where i.id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void updateByIdToTrue(int id) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("update Item i set i.done = true where i.id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void updateByIdToFalse(int id) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("update Item i set i.done = false where i.id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Item> findAllItems() {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item ").list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Item> findTrueItems() {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item i where i.done = true ").list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Item> findFalseItems() {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List result = session.createQuery("from Item i where i.done = false ").list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            List<Item> result = session.createQuery(key, Item.class).list();
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            Item result = session.get(Item.class, id);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void update(Item item) {
        Session session = sf.openSession();
        try (session) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
