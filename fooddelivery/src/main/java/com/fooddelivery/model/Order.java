package com.fooddelivery.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private int orderId;
    private String customerName;
    private String address;
    private String phone;
    private List<CartItem> items;
    private double totalAmount;
    private String status;
    private String orderTime;

    public Order() {
        this.status = "Confirmed";
        this.orderTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOrderTime() { return orderTime; }
    public void setOrderTime(String orderTime) { this.orderTime = orderTime; }

    // Inner class for cart items
    public static class CartItem {
        private int foodId;
        private String foodName;
        private int quantity;
        private double price;

        public CartItem() {}

        public CartItem(int foodId, String foodName, int quantity, double price) {
            this.foodId = foodId;
            this.foodName = foodName;
            this.quantity = quantity;
            this.price = price;
        }

        public int getFoodId() { return foodId; }
        public void setFoodId(int foodId) { this.foodId = foodId; }

        public String getFoodName() { return foodName; }
        public void setFoodName(String foodName) { this.foodName = foodName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public double getSubtotal() { return price * quantity; }
    }
}
