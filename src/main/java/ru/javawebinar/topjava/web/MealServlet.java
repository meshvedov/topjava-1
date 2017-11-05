package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealWithExceedListDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        request.setAttribute("meal", MealWithExceedListDB.mealWithExceeds);
        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, resp);
    }
}
