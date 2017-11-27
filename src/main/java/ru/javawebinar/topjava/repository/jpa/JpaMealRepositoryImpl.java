package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter("user_id", userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.createNamedQuery(Meal.BY_ID, Meal.class).setParameter("id", id).setParameter("user_id", userId).getSingleResult();
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> list = em.createNamedQuery(Meal.ALL, Meal.class).setParameter("user_id", userId).getResultList();
        return list;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET_BETWEEN, Meal.class).setParameter("user_id", userId).setParameter("start_date", startDate).setParameter("end_date", endDate).getResultList();

        return list;
    }
}