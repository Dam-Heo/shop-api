##지원자 : 허담

# allra 백엔드 과제

## 기능
- **상품 조회**: 구매 가능한 상품 목록을 불러옵니다.
- **장바구니**: 상품을 장바구니에 추가하고 수량을 관리합니다.
- **주문 및 결제**: 주문을 제출하고 결제를 처리합니다.
- **주문 내역 조회**: 사용자의 완료된 주문 기록을 조회합니다.

## 기술 스택
- Java 21
- Spring Boot 3.4.3
- MariaDB 10.6.21
- JPA (Java Persistence API)
- Git

```mermaid

```ERD

erDiagram
    CUSTOMER {
        Long id
        String name
        String email
        String password
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    PRODUCT {
        Long id
        String name
        String description
        Long price
        int stock
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    CART {
        Long id
        Long customerId
        Long productId
        int quantity
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    ORDER {
        Long id
        Long customerId
        Long totalPrice
        String status
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    ORDER_DETAIL {
        Long id
        Long orderId
        Long productId
        int quantity
        Long price
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    PAYMENT_HISTORY {
        Long id
        Long customerId
        Long orderId
        Long amount
        String paymentMethod
        String status
        String transactionId
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }

    CUSTOMER ||--o{ ORDER : has
    CUSTOMER ||--o{ CART : has
    CUSTOMER ||--o{ PAYMENT_HISTORY : has
    ORDER ||--o{ ORDER_DETAIL : contains
    ORDER ||--o{ PAYMENT_HISTORY : generates
    PRODUCT ||--o{ CART : contains

```

##API 엔드포인트
# 상품 조회
GET /products

# 장바구니에 상품 추가
POST /cart

# 장바구니 상품 수량 수정
PUT /cart/{id}

# 장바구니에서 상품 제거
DELETE /cart/{id}

# 주문 생성 및 결제 요청
POST /orders

# 특정 고객의 주문 내역 조회
GET /orders/{customerId}

# 장바구니 항목 주문 및 결제 요청
POST /orders/{customerId}/cart-to-order

# 특정 고객의 결제 이력 조회
GET /payment-history/{customerId}

