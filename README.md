# SpringBoot_JWT_Authrization_Service

This branch is code generate new JWT token and validata token genererated from AUTH0 as well belwo are step:

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
