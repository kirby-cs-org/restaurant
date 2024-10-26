CREATE TABLE user (
    user_id VARCHAR(36) PRIMARY KEY,
    password VARCHAR(255),
    phone VARCHAR(15),
    user_role VARCHAR(50),
    username VARCHAR(50)
);

CREATE TABLE receipt (
    b_id VARCHAR(36) PRIMARY KEY,
    created_at TIMESTAMP,
    b_total DECIMAL(10, 2)
);

CREATE TABLE `order` (
    o_id VARCHAR(36) PRIMARY KEY,
    payment_link TEXT,
    o_status VARCHAR(50),
    o_total DECIMAL(10, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    b_id VARCHAR(36),
    user_id VARCHAR(36),
    FOREIGN KEY (b_id) REFERENCES receipt(b_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE food (
    f_id VARCHAR(36) PRIMARY KEY,
    f_image VARCHAR(255),
    f_name VARCHAR(100),
    f_price DECIMAL(10, 2),
    f_status VARCHAR(50)
);

CREATE TABLE ingredient (
    i_id VARCHAR(36) PRIMARY KEY,
    i_price DECIMAL(10, 2),
    expire_date DATE,
    i_image VARCHAR(255),
    i_name VARCHAR(100),
    i_qty INT,
    i_status VARCHAR(50)
);

CREATE TABLE recipe (
    qty INT,
    f_id VARCHAR(36),
    i_id VARCHAR(36),
    PRIMARY KEY (f_id, i_id),
    FOREIGN KEY (f_id) REFERENCES food(f_id),
    FOREIGN KEY (i_id) REFERENCES ingredient(i_id)
);

CREATE TABLE order_line (
    o_qty INT,
    f_id VARCHAR(36),
    o_id VARCHAR(36),
    PRIMARY KEY (f_id, o_id),
    FOREIGN KEY (f_id) REFERENCES food(f_id),
    FOREIGN KEY (o_id) REFERENCES `order`(o_id)
);

CREATE TABLE financial (
    date DATE PRIMARY KEY,
    income DECIMAL(10, 2),
    expense DECIMAL(10, 2),
    total DECIMAL(10, 2)
);
