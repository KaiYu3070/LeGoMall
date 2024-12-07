# Legou Mall

Legou Mall is a B2B2C e-commerce platform, similar to JD.com or Taobao. Merchants can register and manage their own products on the platform. The platform is divided into three main parts: the frontend website, the operator backend, and the merchant backend.

## Project Structure

### 1. Frontend Website
- **Product Display**: Users can browse products, search for items, and view categories.
- **Shopping Cart**: Allows users to add items to the cart and proceed to checkout.
- **Order Management**: Users can view their order history and details.
- **Payment Integration**: Supports integrated payment methods, such as Alipay and WeChat Pay.

### 2. Operator Backend
- **Merchant Management**: Operators can manage merchant registrations, approvals, and information.
- **Product Management**: Includes product category management, product approval, and brand management.
- **Order Management**: Supports querying orders, managing logistics, and settlements.

### 3. Merchant Backend
- **Product Management**: Merchants can add, modify, and delete their product listings.
- **Order Management**: Merchants can view orders related to their store.
- **Merchant Settlement**: Merchants can view sales and settlement details.

## Tech Stack

- **Backend Framework**: Spring, SpringMVC, MyBatis, Spring Security
- **Frontend Framework**: AngularJS, Bootstrap
- **Database**: MySQL
- **Message Queue**: RabbitMQ
- **Caching**: Redis
- **Search**: Solr (for product search)
- **Containerization**: Docker (optional)
- **Payment Integration**: Alipay, WeChat Pay (integrated payment gateways)

## Features

### 1. Merchant Registration
Merchants can submit their registration application, which will be approved by the platform. After approval, they can manage their own products.

### 2. Product Management
Merchants can manage their product listings, including adding, modifying, and deleting products. The platform administrators approve the products before they are published.

### 3. Order Management
Users can generate orders, make payments, and track them. Merchants can process and fulfill orders.

### 4. Search and Categorization
Users can search for products using keywords, categories, and brands, and browse products by category.

### 5. Payment and Settlement
The platform integrates Alipay and WeChat Pay for user payments, and merchants can view their earnings and settlement records.

## Installation and Setup

### 1. Clone the Repository

Clone the project repository to your local machine:

```bash
git clone https://github.com/KaiYu3070/LeGoMall.git
```

### 2. Set Up Database

Make sure you have MySQL installed and create the necessary database and tables. You can find the SQL scripts in the project folder:

```bash
source database.sql
```

### 3. Configure Environment

Edit the configuration files to set up database connections and other parameters:

```bash
# Edit configuration files
vi src/main/resources/application.properties
```

### 4. Build and Run the Project

Build the project using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

### 5. Access the Application

Once the application is running, you can access it at:

- **Frontend Website**: [http://localhost:8080](http://localhost:8080)
- **Operator Backend**: [http://localhost:8080/admin](http://localhost:8080/admin)
- **Merchant Backend**: [http://localhost:8080/seller](http://localhost:8080/seller)

## API Documentation

### 1. Get Product List

**Request**: `GET /api/products`

**Response**:

```json
{
  "total": 100,
  "products": [
    {
      "id": 1,
      "name": "Product Name",
      "price": 100,
      "description": "Product description"
    },
    ...
  ]
}
```

### 2. User Login

**Request**: `POST /api/login`

**Parameters**:

```json
{
  "username": "user@example.com",
  "password": "password123"
}
```

**Response**:

```json
{
  "success": true,
  "message": "Login successful"
}
```

### 3. Create Order

**Request**: `POST /api/orders`

**Parameters**:

```json
{
  "userId": 1,
  "productIds": [1, 2, 3],
  "totalPrice": 300
}
```

**Response**:

```json
{
  "success": true,
  "message": "Order created successfully"
}
```

 
## Contact

If you have any questions or suggestions, feel free to contact us:

- GitHub: [https://github.com/KaiYu3070] 
