package com.fooddelivery.servlet;

import com.fooddelivery.dao.FoodDeliveryDAO;
import com.fooddelivery.model.Order;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    private final FoodDeliveryDAO dao = new FoodDeliveryDAO();
    private final Gson gson = new Gson();

    // POST /api/orders  -> place a new order
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) body.append(line);
        }

        Order incoming = gson.fromJson(body.toString(), Order.class);
        if (incoming == null || incoming.getCustomerName() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid order payload\"}");
            return;
        }

        // Calculate total from items
        if (incoming.getItems() != null) {
            double total = incoming.getItems().stream()
                .mapToDouble(Order.CartItem::getSubtotal)
                .sum();
            incoming.setTotalAmount(total);
        }

        Order saved = dao.placeOrder(incoming);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(gson.toJson(saved));
    }

    // GET /api/orders  -> list all orders
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String idParam = req.getParameter("id");
        if (idParam != null) {
            Order order = dao.getOrderById(Integer.parseInt(idParam));
            if (order == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Order not found\"}");
            } else {
                resp.getWriter().write(gson.toJson(order));
            }
        } else {
            resp.getWriter().write(gson.toJson(dao.getAllOrders()));
        }
    }

    // Handle preflight CORS
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
