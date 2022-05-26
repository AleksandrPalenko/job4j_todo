package ru.job4j.todo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            /**
             User user = create(new User(5, "Ivan", "qwe123", "01@mail.ru"), sf);
             create(Item.of("Core", "sql", LocalDateTime.now(), false, user), sf);
             for (Item item: findAll(Item.class, sf)) {
             System.out.println(item.getName() + " " + item.getUser().getName());
             }

             /**
             User user = new User(1, "Alex", "ok123", "ok.123@mail.ru");
             session.save(user);
             Item one = new Item(1, "Core", "SQl, Stream API", LocalDateTime.now(), true);
             Item two = new Item(2, "Hibernate", "DB", LocalDateTime.now(), false);
             Item three = new Item(3, "Spring", "Spring boot", LocalDateTime.now(), false);
             session.save(one);
             session.save(two);
             session.save(three);
             /**
             Query query = session.createQuery(
             "update Item i set i.name = :newName, i.created = :newCreated, "
             + " i.done = :newDone where i.id = :fId"
             );
             query.setParameter("newName", "Task 1: Java Core");
             query.setParameter("newCreated", "01-03-2021");
             query.setParameter("newDone", "done");
             query.setParameter("fId", 1);
             query.executeUpdate();
             */
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}

