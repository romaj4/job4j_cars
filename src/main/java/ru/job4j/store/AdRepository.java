package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepository implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private AdRepository() {
    }

    private static final class Lazy {
        private static final AdRepository INST = new AdRepository();
    }

    public static AdRepository instOf() {
        return AdRepository.Lazy.INST;
    }

    private <T> T apply(final Function<Session, T> command) {
        final Session session = this.sf.openSession();
        session.beginTransaction();
        T rsl = command.apply(session);
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public <T> T saveElement(T element) {
        this.apply(session -> session.save(element));
        return element;
    }

    public List<Advert> findAdsLastDay() {
        int dayInMillis = 24 * 60 * 60 * 1000;
        return this.apply(session -> session.createQuery(
                "from Advert ad where created between :yest and :now", Advert.class)
                .setParameter("now", new Date(System.currentTimeMillis()))
                .setParameter("yest", new Date(System.currentTimeMillis() - dayInMillis))
                .list());
    }

    public List<Advert> findAdsWithPhoto() {
        return this.apply(session -> session.createQuery(
                "from Advert ad where ad.photo IS NOT NULL", Advert.class).list());
    }

    public List<Advert> findAdsByModel(String brand) {
        return this.apply(session -> session.createQuery(
                "select distinct ad from Advert ad join fetch ad.car car where car.brand=:c_br", Advert.class)
                .setParameter("c_br", brand)
                .list());
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(this.registry);
    }
}
