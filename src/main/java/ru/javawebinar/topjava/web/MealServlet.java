package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImp;
import ru.javawebinar.topjava.dao.MealDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static String INSERT_OR_DELETE = "/meal.jsp";
    private static String LIST_MEAL = "/listMeal.jsp";
    private MealDAO mealDAO;

    public MealServlet() {
        mealDAO = new MealDAOImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int mealID = Integer.parseInt(request.getParameter("mealID"));
            mealDAO.deleteMeal(mealID);

            forward = LIST_MEAL;
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meal", mealWithExceeds);

        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_DELETE;
            int mealID = Integer.parseInt(request.getParameter("mealID"));
            Meal meal = mealDAO.getMealById(mealID);
            request.setAttribute("meal", meal);

        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meal", mealWithExceeds);

        } else {
            forward = INSERT_OR_DELETE;
        }

        getServletContext().getRequestDispatcher(forward).forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        String mealID = req.getParameter("mealID");
        if (mealID == null || mealID.isEmpty())
            mealDAO.addMeal(new Meal(MealDB.atomicInteger.getAndIncrement(), dateTime, description, calories));
        else
            mealDAO.updateMeal(new Meal(Integer.parseInt(mealID), dateTime, description, calories));

        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(mealDAO.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meal", mealWithExceeds);
        getServletContext().getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}
