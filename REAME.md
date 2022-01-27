# Ordering System for Restaurants (OSR)

This is an ordering System for restaurants. In this application you can:
- Register new order by a post request `/api/order`. The payload will be:

```javascript
{
  "id":"ABCD123456",
  "status":"Heard",
  "amount":20.50
  "orderItems" : [
  {
    "meal":
    {
    	"id":""
    	"description":"Tortilla de patata"
    	"unitPrice":5
    }
    "quantity": 2
  },
  {
    "meal":
    {
    	"id":""
    	"description":"Paella"
    	"unitPrice":11.5
    }
    "quantity": 1
  }
  ]
}
```

- Update order status by a put request `/api/order`. They payload will be:

```javascript
{
  "id":"ABCD123456",
  "status":"Cooking"
}
```

- Check order status by a get request `/api/order`, passing de order id as a request parameter.


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

- Create basic project structure :heavy_check_mark:
- Implement Register new order :white_check_mark:
- Implement Update order status :white_check_mark:
- Implement Ckeck order status :white_check_mark:
- Dockerization :white_check_mark:
