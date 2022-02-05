# Ordering System for Restaurants (OSR)

This is an ordering System for Restaurants.

To **run** the project, execute from the console: `mvn clean spring-boot:run`

To run the **test** project, execute from the console: `mvn clean test`

### Business Logic

Possible order status:
- `Heard`
- `Cooking`
- `On the way`
- `Enjoy your meal`
- `Cancelled`

The normal sequence status is: 
1. `Heard`
2. `Cooking`
3. `On the way`
4. `Enjoy your meal`

##### Business Rules
* When an order is create is set by default with `Heard` status

* When the status is checked, if the last time that status has change is over than 5 minutes, order status will change automatically to the next status in the sequence

* `Cancelled` status only can set by the cancel status action


### Concepts used

- Hexagonal architecture
- DDD
- TDD

### Operations

- Create a new order by a POST request `/api/order`.

The payload will be:

```javascript
  {
   "paymentMethod":"PAYPAL",
   "orderItmes":[
      {
         "productId":"31edab93-84c0-48fc-866a-702fc984dc0a",
         "description":"Tortilla de patata",
         "unitPrice":{
            "value":5,
            "currencyCode":"EUR"
         },
         "quantity":1
      },
      {
         "productId":"9e3b6d9b-7ff0-443f-940e-f1c9cd34fa3e",
         "description":"Paella",
         "unitPrice":{
            "value":6,
            "currencyCode":"EUR"
         },
         "quantity":1
      }
   ],
   "addres":{
      "address":"Av. Puerta de Hierro, s/n",
      "city":"Madrid",
      "zipCode":"28071"
   }
}
```

The response will be:

```javascript
{
    "id": "7d37fdb3-0b21-4658-85ee-b1ed640cd17e"
}
```

- Update order status by a PUT request `/api/order`.

They payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
}
```

The response will be:

```javascript
{
    "id": "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454",
    "status": "COOKING"
}
```

- Cancel order PUT request `/api/order/cancel`

They payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
}
```

The response will be a 200 HTTP Status

- Check order status by a GET request `/api/order/status`.

The payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
}
```

The response will be:

```javascript
{
    "id": "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454",
    "status": "COOKING"
}
```

- Get order by a GET request `/api/order/`.

The payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
}
```

The response will be:

```javascript
{
    "order": {
        "orderId": {
            "id": "d3449b20-8615-4c26-92d8-d8ff20b7aaca"
        },
        "status": "HEARD",
        "paymentMethod": "PAYPAL",
        "date": "2022-02-04T13:58:23.716+00:00",
        "modified": "2022-02-04T13:58:23.716+00:00",
        "addres": {
            "address": "Av. Puerta de Hierro, s/n",
            "city": "Madrid",
            "zipCode": "28071"
        },
        "orderItems": [
            {
                "orderItemId": {
                    "id": "8eb801c6-0dfa-4ab9-a0b2-4f6367153322"
                },
                "orderId": {
                    "id": "d3449b20-8615-4c26-92d8-d8ff20b7aaca"
                },
                "productId": "31edab93-84c0-48fc-866a-702fc984dc0a",
                "description": "Tortilla de patata",
                "unitPrice": {
                    "value": 5.00,
                    "currencyCode": "EUR"
                },
                "quantity": 1
            },
            {
                "orderItemId": {
                    "id": "4b7d3801-7231-44cf-8c17-86b5468a348b"
                },
                "orderId": {
                    "id": "d3449b20-8615-4c26-92d8-d8ff20b7aaca"
                },
                "productId": "9e3b6d9b-7ff0-443f-940e-f1c9cd34fa3e",
                "description": "Paella",
                "unitPrice": {
                    "value": 6.00,
                    "currencyCode": "EUR"
                },
                "quantity": 1
            }
        ]
    }
}
```


#### TODO List

- Application definition :heavy_check_mark:
- Create basic project structure: :heavy_check_mark:
- Implement Create new order business logic :heavy_check_mark:
- Implement Create new order api entrypoint :heavy_check_mark:
- Implement Update order status business logic :heavy_check_mark:
- Implement Update order status api entrypoint :heavy_check_mark:
- Implement Cancel order business logic :heavy_check_mark:
- Implement Cancel order api entrypoint :heavy_check_mark:
- Implement Check order status business logic :heavy_check_mark:
- Implement Check order status api entrypoint :heavy_check_mark:
- Implement Get order status business logic :heavy_check_mark:
- Implement Get order status api entrypoint :heavy_check_mark:
- Dockerization :x:
