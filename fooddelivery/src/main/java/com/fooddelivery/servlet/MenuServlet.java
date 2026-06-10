package com.fooddelivery.servlet;

import com.fooddelivery.dao.FoodDeliveryDAO;
import com.fooddelivery.model.FoodItem;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/menu")
public class MenuServlet extends HttpServlet {

    private final FoodDeliveryDAO dao = new FoodDeliveryDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // Allow cross-origin calls during local testing
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String category = req.getParameter("category");
        List<FoodItem> items = (category != null && !category.isEmpty())
            ? dao.getItemsByCategory(category)
            : dao.getAllMenuItems();

        resp.getWriter().write(gson.toJson(items));
    }
}
