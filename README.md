# SpringBoot_JWT_Authrization_Service

This branch will generate new JWT token and validata token genererated from AUTH0 as well belwo are step:

1. If you want to generate new token
   - Take latest code
   - Start server
   - URL: localhost:8080/authenticate (post)
   - POST BODY:
     ```
            {
              "userName": "Test1",
              "password": "value1"
             }
 2. New newly generated token needs to be validated you can use below get URL
    - localhost:8080/validate
    - In header send: 
      - key: authorization value: 
