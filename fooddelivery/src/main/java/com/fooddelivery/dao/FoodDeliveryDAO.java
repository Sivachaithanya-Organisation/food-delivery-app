package com.fooddelivery.dao;

import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * In-memory data store for food items and orders.
 * No database needed - data lives in memory for this demo.
 */
public class FoodDeliveryDAO {

    private static final List<FoodItem> menuItems = new ArrayList<>();
    private static final List<Order> orders = new ArrayList<>();
    private static final AtomicInteger orderIdCounter = new AtomicInteger(1001);

    // Seed menu on class load
    static {
        menuItems.add(new FoodItem(1, "Margherita Pizza",     "Pizza",   299.0, "Classic tomato & mozzarella"));
        menuItems.add(new FoodItem(2, "Pepperoni Pizza",      "Pizza",   349.0, "Loaded with pepperoni slices"));
        menuItems.add(new FoodItem(3, "BBQ Chicken Burger",   "Burger",  199.0, "Grilled chicken with BBQ sauce"));
        menuItems.add(new FoodItem(4, "Veg Burger",           "Burger",  149.0, "Fresh veggies in a sesame bun"));
        menuItems.add(new FoodItem(5, "Chicken Biryani",      "Biryani", 249.0, "Aromatic basmati with chicken"));
        menuItems.add(new FoodItem(6, "Veg Biryani",          "Biryani", 199.0, "Fragrant rice with mixed veggies"));
        menuItems.add(new FoodItem(7, "Paneer Butter Masala", "Curry",   229.0, "Creamy paneer in tomato gravy"));
        menuItems.add(new FoodItem(8, "Dal Tadka",            "Curry",   149.0, "Yellow lentils with tempering"));
        menuItems.add(new FoodItem(9, "Masala Dosa",          "South Indian", 129.0, "Crispy dosa with potato filling"));
        menuItems.add(new FoodItem(10,"Idli Sambar",          "South Indian", 99.0,  "Steamed idlis with sambar"));
        menuItems.add(new FoodItem(11,"Chocolate Lava Cake",  "Dessert", 149.0, "Warm cake with molten center"));
        menuItems.add(new FoodItem(12,"Mango Lassi",          "Drinks",  79.0,  "Chilled mango yogurt drink"));
    }

    public List<FoodItem> getAllMenuItems() {
        return new ArrayList<>(menuItems);
    }

    public List<FoodItem> getItemsByCategory(String category) {
        return menuItems.stream()
            .filter(item -> item.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }

    public FoodItem getItemById(int id) {
        return menuItems.stream()
            .filter(item -> item.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public List<String> getCategories() {
        return menuItems.stream()
            .map(FoodItem::getCategory)
            .distinct()
            .collect(Collectors.toList());
    }

    public Order placeOrder(Order order) {
        order.setOrderId(orderIdCounter.getAndIncrement());
        orders.add(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Order getOrderById(int orderId) {
        return orders.stream()
            .filter(o -> o.getOrderId() == orderId)
            .findFirst()
            .orElse(null);
    }
}
