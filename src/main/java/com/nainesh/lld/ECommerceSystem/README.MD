# E-commerce Application (LLD Problem Statement)

Assumptions:
- Users can browse products and place orders.
- Products have stock counts.
- Orders are placed from a single seller at a time (ignore multi-seller carts).
- Payments, shipping, and discounts are ignored for simplicity.

APIs:
- String placeOrder(String userId, List<Item> items)
- boolean cancelOrder(String orderId)
- List<Product> searchProducts(String keyword) -catalog
- int checkStock(String productId)

Focus Areas:
- Validate product availability before confirming an order.
- Prevent overselling by handling concurrent requests safely.
- Maintain order history per user.
- Keep design extensible for adding features like payments, shipping, or multiple sellers later.

entities: ITEM, ORDER, ORDERITEM (like dto), Product

product -for catalog service
item- Itemid, productId, warehouseId(not used), skuCount
orderItem - productId,skuCount
Order - orderid, productId, userId, isActive
