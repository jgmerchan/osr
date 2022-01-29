# Ordering System for Restaurants (OSR)

This is an ordering System for restaurants. In this application you can:

- Register new order by a POST request `/api/order`. The payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454",
  "status":"Heard",
  "paymentMethod":"paypal",
  "amount":20.50,
  "date":"28/01/2022 13:43",
  "address":
  {
  	"address":"Av. Puerta de Hierro, s/n",
  	"city":"Madrid",
  	"zipCode":"28071"
  },
  "orderItems" : [
  {
    "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3452",
    "description":"Tortilla de patata",
    "unitPrice":5,
    "quantity": 2
  },
  {
    "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3453",
    "description":"Paella",
    "unitPrice":11.5,
    "quantity": 1
  }
  ]
}
```

- Update order status by a PUT request `/api/order`. They payload will be:

```javascript
{
  "id":"f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454",
  "status":"Cooking"
}
```

- Check order status by a GET request `/api/order`, passing de order id as a request parameter.


### Business Logic

Possible orders status:
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
* When an order is create is set by default whit `Heard` status

* When the status is checked, if the last time that status has change is over than 5 minutes, order status will change automatically to the next status in the sequence

* `Cancelled` status only can set by the update status action


### Concepts used

- Hexagonal architecture
- DDD
- TDD


#### TODO List

- Application definition :heavy_check_mark:
- Create basic project structure: :x:
- Implement Register new order :x:
- Implement Update order status :x:
- Implement Check order status :x:
- Dockerization :x:
