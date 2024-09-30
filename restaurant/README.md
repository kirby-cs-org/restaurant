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
- **POST** /api/order-lines                   # สร้างราคารวมต่อรายการอาหาร (ใช้ Body)     - **for customer**
- **GET** /api/order-lines                    # ดูรายการออเดอร์ทั้งหมด                    - **for admin**
- **GET** /api/order-lines/order/{orderId}    # ดูรายการตามออเดอร์                       - **for customer**
- **DELETE** /api/order-lines/{id}             # ลบรายการในออเดอร์                         - **for admin**

### Transaction API
- **POST** /api/transactions                   # สร้างการชำระเงินใหม่ (ใช้ Body)           - **for customer**
- **GET** /api/transactions                    # ดูการชำระเงินทั้งหมด                      - **for admin**
- **GET** /api/transactions/order/{orderId}    # ดูการชำระเงินตามออเดอร์                  - **for admin**

### Financial API
- **POST** /api/financials                     # สร้างรายรับรายจ่ายใหม่ (ใช้ Body)        - **for admin**
- **GET** /api/financials                      # ดูรายรับรายจ่ายทั้งหมด                   - **for admin**
- **PUT** /api/financials/{id}                 # อัพเดทข้อมูลรายรับรายจ่าย (ใช้ Body)    - **for admin**
- **DELETE** /api/financials/{id}              # ลบข้อมูลรายรับรายจ่าย                    - **for admin**
