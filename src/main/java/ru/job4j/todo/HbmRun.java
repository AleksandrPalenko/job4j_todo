package ru.job4j.todo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.time.LocalDateTime;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

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
}

