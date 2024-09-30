# API Paths

### Ingredient API
- **POST** /api/ingredients                   # สร้างวัตถุดิบใหม่ (ใช้ Body)                  - **for admin**
- **GET** /api/ingredients                    # ดูวัตถุดิบทั้งหมด                            - **for admin**
- **GET** /api/ingredients/status/{status}    # ดูวัตถุดิบตามสถานะ (หมด, เหลือ)             - **for admin**
- **GET** /api/ingredients/{id}               # ดูวัตถุดิบตาม ID                             - **for admin**
- **PUT** /api/ingredients/{id}/quantity       # อัพเดทจำนวนวัตถุดิบ (ใช้ Body)              - **for admin**
- **PATCH** /api/ingredients/{id}/status       # เปลี่ยนสถานะวัตถุดิบ (ใช้ Body)             - **for admin**
- **DELETE** /api/ingredients/{id}             # ลบวัตถุดิบ                                  - **for admin**

### Customer API
- **POST** /api/customers                      # สมัครสมาชิก (ใช้ Body)                       - **public access**
- **GET** /api/customers                       # ดูรายชื่อสมาชิกทั้งหมด                     - **for admin**
- **PUT** /api/customers/{id}                  # อัพเดทรายละเอียดสมาชิก (ใช้ Body)         - **for admin**
- **DELETE** /api/customers/{id}               # ลบสมาชิก                                    - **for admin**

### Food API
- **POST** /api/foods                         # สร้างเมนูอาหารใหม่ (ใช้ Body)              - **for admin**
- **GET** /api/foods                          # ดูเมนูทั้งหมด                               - **public access**
- **GET** /api/foods/status/{status}          # ดูเมนูตามสถานะ                             - **public access**
- **PATCH** /api/foods/{id}/status             # เปลี่ยนสถานะเมนูอาหาร (ใช้ Body)         - **for admin**
- **DELETE** /api/foods/{id}                   # ลบอาหาร                                    - **for admin**

### Receipt API
- **POST** /api/receipts                      # สร้างใบเสร็จใหม่ (ใช้ Body)                - **for admin**
- **GET** /api/receipts                       # ดูใบเสร็จทั้งหมด                           - **for admin**
- **GET** /api/receipts/{id}                  # ดูใบเสร็จตาม ID                            - **for admin**

### Recipe API
- **POST** /api/recipes                       # สร้างสูตรอาหารใหม่ (ใช้ Body)              - **for admin**
- **GET** /api/recipes                        # ดูสูตรอาหารทั้งหมด                        - **public access**
- **GET** /api/recipes/menu/{menuId}         # ดูสูตรอาหารตามเมนู                       - **public access**
- **PUT** /api/recipes/{id}/ingredients        # อัพเดทจำนวนวัตถุดิบในสูตร (ใช้ Body)     - **for admin**
- **DELETE** /api/recipes/{id}                 # ลบสูตรอาหาร                               - **for admin**

### Order API
- **POST** /api/orders                        # สร้างออเดอร์ใหม่ (ใช้ Body)                - **for customer**
- **GET** /api/orders                         # ดูออเดอร์ทั้งหมด                           - **for admin**
- **GET** /api/orders/status/{status}         # ดูออเดอร์ตามสถานะ                         - **for admin**
- **PATCH** /api/orders/{id}/status            # อัพเดทสถานะออเดอร์ (ใช้ Body)           - **for admin**

### Order Line API
- **POST** /api/orders-lines                   # สร้างราคารวมต่อรายการอาหาร (ใช้ Body)     - **for customer**
- **GET** /api/orders-lines                    # ดูรายการออเดอร์ทั้งหมด                    - **for admin**
- **GET** /api/orders-lines/orders/{orderId}    # ดูรายการตามออเดอร์                       - **for customer**
- **DELETE** /api/orders-lines/{id}             # ลบรายการในออเดอร์                         - **for admin**

### Transaction API
- **POST** /api/transactions                   # สร้างการชำระเงินใหม่ (ใช้ Body)           - **for customer**
- **GET** /api/transactions                    # ดูการชำระเงินทั้งหมด                      - **for admin**
- **GET** /api/transactions/orders/{orderId}    # ดูการชำระเงินตามออเดอร์                  - **for admin**

### Financial API
- **POST** /api/financials                     # สร้างรายรับรายจ่ายใหม่ (ใช้ Body)        - **for admin**
- **GET** /api/financials                      # ดูรายรับรายจ่ายทั้งหมด                   - **for admin**
- **PUT** /api/financials/{id}                 # อัพเดทข้อมูลรายรับรายจ่าย (ใช้ Body)    - **for admin**
- **DELETE** /api/financials/{id}              # ลบข้อมูลรายรับรายจ่าย                    - **for admin**

# Fields in the ER Diagram

- **Receipt**
    - receipt_id (Primary Key)    # เปลี่ยนชื่อเป็น receipt_id
    - total_amount                # เปลี่ยนชื่อเป็น total_amount
    - transaction_time             # เปลี่ยนชื่อเป็น transaction_time
    - transaction_date             # เปลี่ยนชื่อเป็น transaction_date
    - payment_amount               # เปลี่ยนชื่อเป็น payment_amount
    - order_id (Foreign Key)      # อ้างอิงจาก Order(order_id)

- **Transaction**
    - transaction_id (Primary Key) # เปลี่ยนชื่อเป็น transaction_id
    - total_amount                 # เปลี่ยนชื่อเป็น total_amount
    - qr_code_image                # เปลี่ยนชื่อเป็น qr_code_image
    - created_at                   # ไม่เปลี่ยนแปลง
    - receipt_id (Foreign Key)     # อ้างอิงจาก Receipt(receipt_id)

- **Order**
    - order_id (Primary Key)       # เปลี่ยนชื่อเป็น order_id
    - total_amount                 # เปลี่ยนชื่อเป็น total_amount
    - order_status                 # เปลี่ยนชื่อเป็น order_status
    - updated_at                   # ไม่เปลี่ยนแปลง
    - user_id (Foreign Key)        # อ้างอิงจาก User(user_id)

- **OrderLine**
    - quantity                     # เปลี่ยนชื่อเป็น quantity
    - total_price                  # เปลี่ยนชื่อเป็น total_price
    - order_id (Foreign Key)       # อ้างอิงจาก Order(order_id)
    - food_id (Foreign Key)        # อ้างอิงจาก Food(food_id)

- **Financial**
    - financial_id (Primary Key)   # เปลี่ยนชื่อเป็น financial_id
    - record_date                  # เปลี่ยนชื่อเป็น record_date
    - expense_amount               # เปลี่ยนชื่อเป็น expense_amount
    - income_amount                # เปลี่ยนชื่อเป็น income_amount
    - total_amount                 # ไม่เปลี่ยนแปลง
    - receipt_id (Foreign Key)     # อ้างอิงจาก Receipt(receipt_id)

- **Ingredient**
    - ingredient_id (Primary Key)  # เปลี่ยนชื่อเป็น ingredient_id
    - ingredient_name              # เปลี่ยนชื่อเป็น ingredient_name
    - quantity_available            # เปลี่ยนชื่อเป็น quantity_available
    - amount_needed                 # เปลี่ยนชื่อเป็น amount_needed
    - ingredient_status            # เปลี่ยนชื่อเป็น ingredient_status
    - expiration_date              # เปลี่ยนชื่อเป็น expiration_date
    - last_updated                 # เปลี่ยนชื่อเป็น last_updated

- **Food**
    - food_id (Primary Key)        # เปลี่ยนชื่อเป็น food_id
    - food_name                    # เปลี่ยนชื่อเป็น food_name
    - food_price                   # เปลี่ยนชื่อเป็น food_price
    - food_image                   # เปลี่ยนชื่อเป็น food_image
    - food_status                  # เปลี่ยนชื่อเป็น food_status
    - created_at                   # ไม่เปลี่ยนแปลง
    - updated_at                   # ไม่เปลี่ยนแปลง

- **User**                       # เปลี่ยนชื่อจาก Customer เป็น User
    - user_id (Primary Key)        # เปลี่ยนชื่อเป็น user_id
    - user_name                    # เปลี่ยนชื่อเป็น user_name
    - user_phone                   # เปลี่ยนชื่อเป็น user_phone
    - user_password                # เปลี่ยนชื่อเป็น user_password
    - created_at                   # ไม่เปลี่ยนแปลง
    - role (enum: customer, admin) # เพิ่ม role สำหรับ JWT
    - refresh_token (string)       # เพิ่ม refresh token
    - refresh_token_expiry (datetime) # เพิ่มวันหมดอายุสำหรับ refresh token

- **JWT Token**
    - jwt_token (string)           # เปลี่ยนชื่อเป็น jwt_token
    - expiry_date (datetime)       # ไม่เปลี่ยนแปลง
