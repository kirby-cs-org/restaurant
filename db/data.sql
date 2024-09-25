INSERT INTO `ingredient` (i_name, i_amount, i_status, i_qty, expire_date) VALUES
('Tomato', 100, 'available', 10, '2024-10-15'),
('Cheese', 50, 'available', 5, '2024-09-30'),
('Flour', 200, 'out_of_stock', 0, '2024-11-20'),
('Olive Oil', 30, 'available', 2, '2025-01-10'),
('Basil', 80, 'available', 8, '2024-10-25');

INSERT INTO `customer` (c_id, c_name, c_phone, c_password) VALUES
('d1f7b1d7-a2b3-41a6-bb7e-2d4f6d71f290', 'John Doe', '123-456-7890', 'password123'),
('ff0b567f-9157-4a42-8d42-7649f278ed10', 'Jane Smith', '098-765-4321', 'securePass456'),
('cc43c8f4-09a3-452d-81ba-8f4f1254cbb9', 'Michael Brown', '555-555-5555', 'myPassword789'),
('a9bfb2c2-e917-4e4d-9eaf-324f25dbd52e', 'Emily Davis', '222-333-4444', 'superSecure123'),
('a5fda8cd-03e6-4f93-9b56-9c48f8a8e143', 'Sarah Johnson', '111-222-3333', 'passwordSecure');

INSERT INTO `product` (p_id, p_qty, p_price, p_name, p_status) VALUES
('f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832', 50, 12.99, 'Pizza Margherita', 'available'),
('5f3a8e8c-d2d3-4729-bbf1-7687f38d0d94', 30, 8.49, 'Garlic Bread', 'available'),
('e83c8f9f-f147-4b2e-a9c5-6f217c5bf4b1', 20, 6.99, 'Caesar Salad', 'available'),
('0f2c7d1f-1dfd-4716-8ae5-07d2e9c03df4', 0, 14.99, 'BBQ Chicken Pizza', 'out_of_stock'),
('5d7e9fa6-f7d4-4c73-8b29-2a2189e4d72c', 15, 7.99, 'Bruschetta', 'available');

INSERT INTO `receipt` (b_id, b_time, b_date, b_amount, b_total) VALUES
('1f2c5b3a-8f2d-42a5-bc2e-fd2b5d3a6f93', '18:30:00', '2024-09-25', 3, 38.97),
('2d4e7b8c-a2e9-48d4-bd5e-9c24e8b2d1f7', '12:15:00', '2024-09-24', 1, 8.49),
('3f5d9b2a-e4b6-45a3-bf3e-cb7f5d6b8e24', '14:50:00', '2024-09-23', 2, 29.98),
('4f7c8e3b-b6e7-4f24-bc8d-a2e5d1f6b6f7', '19:05:00', '2024-09-22', 4, 47.96),
('5e9f3c2d-d8b5-4c8f-ad3e-e1f2c5d9e8f5', '11:20:00', '2024-09-21', 1, 7.99);

INSERT INTO `recipe` (r_qty, p_id, i_name) VALUES
(20, 'f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832', 'Tomato'),
(10, 'f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832', 'Cheese'),
(30, '5f3a8e8c-d2d3-4729-bbf1-7687f38d0d94', 'Flour'),
(10, 'e83c8f9f-f147-4b2e-a9c5-6f217c5bf4b1', 'Olive Oil'),
(0, '5d7e9fa6-f7d4-4c73-8b29-2a2189e4d72c', 'Basil');

INSERT INTO `order` (o_id, o_qty, o_status, o_timestamp) VALUES
('91f2b8a4-2d9f-4f85-b9b2-2d8f6b7e924c', 3, 'delivered', '2024-09-25 18:45:00'),
('7f2b9d8e-b1f3-4e6d-a3f5-9c5e7d2b5f1a', 1, 'pending', '2024-09-25 12:30:00'),
('3c5f7b9a-e7f9-4b4e-9d1b-cf5b3e6b8f24', 2, 'preparing', '2024-09-25 14:00:00'),
('8d1f9e5a-b4d7-4c9e-8d7a-7e9f4b5d2c7a', 4, 'out_for_delivery', '2024-09-25 19:15:00'),
('9e2f3c6d-e8f5-4a2b-9b7c-f3d2e9c4f1a7', 1, 'cancelled', '2024-09-24 17:10:00');

INSERT INTO `order_line` (total_price, o_id, p_id) VALUES
(38.97, '91f2b8a4-2d9f-4f85-b9b2-2d8f6b7e924c', 'f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832'),
(8.49, '7f2b9d8e-b1f3-4e6d-a3f5-9c5e7d2b5f1a', '5f3a8e8c-d2d3-4729-bbf1-7687f38d0d94'),
(14.99, '3c5f7b9a-e7f9-4b4e-9d1b-cf5b3e6b8f24', '0f2c7d1f-1dfd-4716-8ae5-07d2e9c03df4'),
(47.96, '8d1f9e5a-b4d7-4c9e-8d7a-7e9f4b5d2c7a', 'e83c8f9f-f147-4b2e-a9c5-6f217c5bf4b1'),
(7.99, '9e2f3c6d-e8f5-4a2b-9b7c-f3d2e9c4f1a7', '5d7e9fa6-f7d4-4c73-8b29-2a2189e4d72c');

INSERT INTO `transaction` (t_id, t_amount, qr_image, t_timestamp) VALUES
('a1f9b3e2-1b9f-4f92-b8c3-e2d7f1c3a7f2', 38.97, 'qr_code_image1', '2024-09-25 18:50:00'),
('b2f1c8d7-e5f2-4d8f-b9a1-c4e9d2a5f3c8', 8.49, 'qr_code_image2', '2024-09-25 12:35:00'),
('c3f2d7a8-e4c9-4f2b-b7e8-d5f6a7e4c9b1', 29.98, 'qr_code_image3', '2024-09-23 14:55:00'),
('d4f1e5b7-b5f2-4c9f-b8a9-d3f2a9e7c8b2', 47.96, 'qr_code_image4', '2024-09-22 19:10:00'),
('e5f2b4d8-c7f3-4f9e-a9c2-e7f3d8c6b5a3', 7.99, 'qr_code_image5', '2024-09-21 11:25:00');

INSERT INTO `financial` (f_date, f_expense, f_income, f_total) VALUES
('2024-09-21', 50.00, 100.00, 50.00),
('2024-09-22', 70.00, 200.00, 130.00),
('2024-09-23', 80.00, 150.00, 70.00),
('2024-09-24', 60.00, 180.00, 120.00),
('2024-09-25', 90.00, 250.00, 160.00);




