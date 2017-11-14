package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.id());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealController.create(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
//                repository.delete(id, AuthorizedUser.id());
                mealController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.id()) :
//                        repository.get(getId(request), AuthorizedUser.id());
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String users = request.getParameter("users");
                if (users != null)
                    AuthorizedUser.setId(Integer.parseInt(users));

                Collection<MealWithExceed> mealWithExceeds;
                String sD = request.getParameter("startDate");
                String eD = request.getParameter("endDate");
                String sT = request.getParameter("startTime");
                String eT = request.getParameter("endTime");
//                if (sd != null || ed != null || sT != null || eT !=null) {
//                    LocalDate startDate = sd != null ? LocalDate.parse(sd) : LocalDate.MIN;
//                    LocalDate endDate = ed != null ? LocalDate.parse(ed) : LocalDate.MAX;
//                    LocalTime startTime = sT != null ? LocalTime.parse(sT) : LocalTime.MIN;
//                    LocalTime endTime = eT != null ? LocalTime.parse(eT) : LocalTime.MAX;
//
//                    mealWithExceeds = mealController.getAll(startDate, )
//                }
                if (sD != null && eD != null && !sD.equals("") && !eD.equals("")) {
                    LocalDate startDate = LocalDate.parse(sD);
                    LocalDate endDate = LocalDate.parse(eD);
                    mealWithExceeds = mealController.getAll(startDate, endDate);
                } else if (sT != null && eT != null && !sT.equals("") && !eT.equals("")) {
                    LocalTime startTime = LocalTime.parse(sT);
                    LocalTime endTime = LocalTime.parse(eT);
                    mealWithExceeds = mealController.getAll(startTime, endTime);
                } else {
                    mealWithExceeds = mealController.getAll();
                }

                request.setAttribute("meals", mealWithExceeds);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
