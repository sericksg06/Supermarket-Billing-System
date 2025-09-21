-- Supermarket Database

CREATE DATABASE SupermarketDB;

USE SupermarketDB;

CREATE TABLE Products (
    ProductID INT PRIMARY KEY,
    Name VARCHAR(50),
    Price DECIMAL(10,2)
);

CREATE TABLE Sales (
    SaleID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT,
    Quantity INT,
    TotalPrice DECIMAL(10,2),
    SaleDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Sample data
INSERT INTO Products (ProductID, Name, Price) VALUES
(1, 'Milk', 50.0),
(2, 'Bread', 30.0),
(3, 'Eggs', 10.0);
