-- สร้างวัตถุใหม่ (insert) C
INSERT INTO `ingredient` (i_name, i_amount, i_status, i_qty, expire_date) VALUES 
('Sugar', 20, 'available', 10, '2025-10-15');

-- ดูวัตถุดิบ (select) R
-- ดูทั้งหมด
SELECT * FROM `ingredient`;
-- ดูตามสถานะ (หมด, เหลือ)
SELECT * FROM `ingredient` WHERE i_status = 'available';
SELECT * FROM `ingredient` WHERE i_status = 'out_of_stock';

-- ลดจำนวน (update) U
UPDATE `ingredient` SET i_qty = i_qty - 1 WHERE i_name = 'Tomato';
-- เพิ่มจำนวน (update) U
UPDATE `ingredient` SET i_qty = i_qty + 1 WHERE i_name = 'Tomato';
-- เปลี่ยนสถานะ (update) U
UPDATE `ingredient` SET i_status = 'available' WHERE i_name = 'Tomato';
UPDATE `ingredient` SET i_status = 'out_of_stock' WHERE i_name = 'Tomato';
-- ลบวัตถุดิบ (delete) D
DELETE FROM `ingredient` WHERE i_name = '';

-- ================================================================================ 

-- สมัครสมาชิก (insert) C
INSERT INTO `customer` (c_id, c_name, c_phone, c_password) VALUES
('d1f7b1d7-a2b3-41a6-bb7e-2d4f6d71f290', 'Banana', '123-456-7899', 'password123');
-- ดูรายชื่อสมาชิก (select) R
SELECT * FROM `customer`;

-- ================================================================================ 

-- สร้างเมนูใหม่ (insert) C
INSERT INTO `product` (p_id, p_qty, p_price, p_name, p_status) VALUES
('f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832', 50, 12.99, 'Pizza Margherita', 'available');
-- ดูเมนูทั้งหมด (select) R
SELECT * FROM `product`;
-- ดูตามสถานะ
SELECT * FROM `product` WHERE p_status = 'available';
SELECT * FROM `product` WHERE p_status = 'out_of_stock';
-- เพิ่มจำนวนเมนู (update) U
UPDATE `product` SET p_qty = p_qty + 1 WHERE p_id = '';
-- ลดจำนวนเมนู (update) U
UPDATE `product` SET p_qty = p_qty - 1 WHERE p_id = '';
-- ลบอาหาร (delete) D
DELETE FROM `product` WHERE p_id = '';

-- ================================================================================ 
-- สร้างใบเสร็จใหม่ (insert) C
INSERT INTO `receipt` (b_id, b_time, b_date, b_amount, b_total) VALUES
('1f2c5b3a-8f2d-42a5-bc2e-fd2b5d3a6f93', '18:30:00', '2024-09-25', 3, 38.97);
-- ดูใบเสร็จ (select) R
SELECT * FROM `receipt`;

-- ================================================================================ 

-- สร้างสูตรอาหารใหม่ (insert) C
INSERT INTO `recipe` (r_qty, p_id, i_name) VALUES
(20, 'f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832', 'Tomato'),
-- ดูสูตรอาหาร (select) R
-- ดูตามเมนู
SELECT product.p_id, product.p_name, ingredient.i_name, r_qty FROM product 
JOIN recipe ON product.p_id = recipe.p_id 
JOIN ingredient ON recipe.i_name = ingredient.i_name;
-- เพิ่มจำนวนวัตถุดิบในสูตร (update) U
UPDATE `recipe` SET r_qty = r_qty + 1 WHERE i_name = 'Basil' AND p_id = '5d7e9fa6-f7d4-4c73-8b29-2a2189e4d72c';
-- ลดจำนวนวัตถุดิบในสูตร (update) U
UPDATE `recipe` SET r_qty = r_qty - 1 WHERE i_name = 'Basil' AND p_id = '5d7e9fa6-f7d4-4c73-8b29-2a2189e4d72c';
-- ลบสูตรอาหาร (delete) D
DELETE FROM `recipe` WHERE i_name = ? AND p_id = ?;

-- ================================================================================ 

-- สร้างออเดอร์ใหม่ (insert) C
INSERT INTO `order` (o_id, o_qty, o_status, o_timestamp) VALUES
('91f2b8a4-2d9f-4f85-b9b2-2d8f6b7e923a', 3, 'delivered', '2024-09-25 18:45:10'),
-- ดูออเดอร์ (select) R
SELECT * FROM `order`;
-- ดูตามสถานะ
SELECT * FROM `order` WHERE i_status = 'pending';
SELECT * FROM `order` WHERE i_status = 'preparing';
SELECT * FROM `order` WHERE i_status = 'out_for_delivery';
SELECT * FROM `order` WHERE i_status = 'delivered';
SELECT * FROM `order` WHERE i_status = 'cancelled';
-- อัพเดทสถานะ (update) U
UPDATE `order` SET o_status = 'pending' WHERE o_id = ?;
UPDATE `order` SET o_status = 'preparing' WHERE o_id = ?;
UPDATE `order` SET o_status = 'out_for_delivery' WHERE o_id = ?;
UPDATE `order` SET o_status = 'delivered' WHERE o_id = ?;
UPDATE `order` SET o_status = 'cancelled' WHERE o_id = ?;
-- ลบออเดอร์
DELETE FROM `order` WHERE o_id = '';

-- ================================================================================ 

-- สร้างราคารวม (insert) C
INSERT INTO `order_line` (total_price, o_id, p_id) VALUES
(38.97, '91f2b8a4-2d9f-4f85-b9b2-2d8f6b7e922e', 'f2d3b2a3-b5b6-48c5-9ac3-bf1e4cbab832'),
-- ดูรายการออเดอร์ (select) R
SELECT 
    `order`.o_qty, 
    `order`.o_status, 
    `order`.o_timestamp, 
    (`order`.o_qty * `product`.p_price) AS total_price, 
    `product`.p_price, 
    `product`.p_name 
FROM 
    `order`
JOIN 
    `order_line` ON `order`.o_id = `order_line`.o_id 
JOIN 
    `product` ON `order_line`.p_id = `product`.p_id;

-- ดูตามออเดอร์ by id
SELECT 
    `order`.o_qty, 
    `order`.o_status, 
    `order`.o_timestamp, 
    (`order`.o_qty * `product`.p_price) AS total_price, 
    `product`.p_price, 
    `product`.p_name 
FROM 
    `order`
JOIN 
    `order_line` ON `order`.o_id = `order_line`.o_id 
JOIN 
    `product` ON `order_line`.p_id = `product`.p_id
WHERE 
    `order`.o_id = '7f2b9d8e-b1f3-4e6d-a3f5-9c5e7d2b5f1a';

-- ลบรายการในออเดอร์ (delete) D
DELETE FROM `order_line` WHERE o_id = '';

-- ================================================================================ 

-- สร้างการชำระเงินใหม่ (insert) C
INSERT INTO `transaction` (t_id, t_amount, qr_image, t_timestamp) VALUES
('a1f9b3e2-1b9f-4f92-b8c3-e2d7f1c3a7f2', 38.97, 'qr_code_image1', '2024-09-25 18:50:00');
-- ดูการชำระเงิน (select) R
SELECT * FROM `transaction`;
-- ================================================================================ 

-- สร้างรายรับรายจ่ายใหม่ (insert) C
INSERT INTO `financial` (f_date, f_expense, f_income, f_total) VALUES ('2024-09-21', 50.00, 100.00, 50.00);
-- ดูรายรับรายจ่าย (select) R
SELECT * FROM `financial`;

-- 65 Querys total






