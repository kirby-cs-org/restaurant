CREATE TABLE `ingredient` (
    i_name VARCHAR(64) PRIMARY KEY,
    i_amount INT NOT NULL CHECK (i_amount >= 0),
    i_status ENUM('out_of_stock', 'available') NOT NULL,
    i_qty INT NOT NULL CHECK (i_qty >= 0),
    expire_date DATE NOT NULL
);

CREATE TABLE `customer` (
    c_id VARCHAR(36) PRIMARY KEY,
    c_name VARCHAR(64) NOT NULL,
    c_phone VARCHAR(16) NOT NULL,
    c_password VARCHAR(64) NOT NULL
);

CREATE TABLE `product` (
    p_id VARCHAR(36) PRIMARY KEY,
    p_qty INT NOT NULL CHECK (p_qty >= 0),
    p_price DECIMAL(6, 2) NOT NULL CHECK (p_price >= 0),
    p_name VARCHAR(64) NOT NULL,
    p_status ENUM('out_of_stock', 'available') NOT NULL
);

CREATE TABLE `receipt` (
    b_id VARCHAR(36) PRIMARY KEY,
    b_time TIME NOT NULL DEFAULT,
    b_date DATE NOT NULL DEFAULT,
    b_amount INT NOT NULL CHECK (b_amount >= 0),
    b_total DECIMAL(6, 2) NOT NULL CHECK (b_total >= 0)
);

CREATE TABLE `recipe` (
    r_qty INT NOT NULL CHECK (r_qty >= 0),
    p_id VARCHAR(36),  
    i_name VARCHAR(64),
    
    FOREIGN KEY (p_id) REFERENCES `product` (p_id),
    FOREIGN KEY (i_name) REFERENCES `ingredient` (i_name) 
);

CREATE TABLE `order` (
    o_id VARCHAR(36) PRIMARY KEY,
    o_qty INT NOT NULL CHECK (o_qty >= 0),
    o_status ENUM('pending' ,'preparing', 'out_for_delivery', 'delivered', 'cancelled') NOT NULL DEFAULT 'pending',
    o_timestamp TIMESTAMP NOT NULL
);

CREATE TABLE `order_line` (
    total_price DECIMAL(6, 2) NOT NULL CHECK (total_price >= 0),
    o_id VARCHAR(36),
    p_id VARCHAR(36),

    FOREIGN KEY (o_id) REFERENCES `order` (o_id),
    FOREIGN KEY (p_id) REFERENCES `product` (p_id)
);

CREATE TABLE `transaction` (
    t_id VARCHAR(36) PRIMARY KEY,
    t_amount DECIMAL(6, 2) NOT NULL CHECK (t_amount >= 0),
    qr_image BLOB NOT NULL,
    t_timestamp TIMESTAMP NOT NULL
);

CREATE TABLE `financial` (
    f_date DATE PRIMARY KEY,
    f_expense DECIMAL(6, 2) NOT NULL CHECK (f_expense >= 0),
    f_income DECIMAL(6, 2) NOT NULL CHECK (f_income >= 0),
    f_total DECIMAL(6, 2) NOT NULL CHECK (f_total >= 0)
);
