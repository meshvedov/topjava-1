package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final TestLogger testLogger = new TestLogger();

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    @Test
    @Transactional
    public void testDelete() throws Exception {
        MEALS.forEach(meal -> meal.setUser(USER));
        service.delete(MEAL1_ID, USER_ID);
        List<Meal> list = service.getAll(USER_ID);
        assertMatch(list, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(MEAL1_ID, 1);
    }

    @Test
    @Transactional
    public void testSave() throws Exception {
        Meal created = getCreated();
        created.setUser(USER);
        service.create(created, USER_ID);
        MEALS.forEach(meal -> meal.setUser(USER));
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    @Transactional
    public void testGet() throws Exception {
        Date start = new Date();
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        ADMIN_MEAL1.setUser(ADMIN);
        assertMatch(actual, ADMIN_MEAL1);
        Date stop = new Date();
        long delta = stop.getTime() - start.getTime();
        Logger log = testLogger.getLogger();
        log.info(String.valueOf(delta));
//        LOG.info(String.valueOf(delta) + " ms");
    }

    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        MEALS.forEach(meal -> meal.setUser(USER));
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        updated.setUser(USER);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }
//    @Test(expected = NotFoundException.class)
    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        MEALS.forEach(meal -> meal.setUser(USER));
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        MEALS.forEach(x -> x.setUser(USER));
        assertMatch(all, MEALS);
    }

    @Test
    @Transactional
    public void testGetBetween() throws Exception {
        MEALS.forEach(meal -> meal.setUser(USER));
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    class TestLogger implements TestRule {
        private Logger logger;

        public Logger getLogger() {
            return logger;
        }

        @Override
        public Statement apply(Statement base, Description description) {

            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    logger = Logger.getLogger(description.getTestClass().getName());
                    base.evaluate();
                }
            };
        }
    }
}