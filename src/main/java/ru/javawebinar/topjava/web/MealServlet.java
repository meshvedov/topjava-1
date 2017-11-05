package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDB;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(MealDB.meals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meal", mealWithExceeds);
        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, resp);
    }
}
