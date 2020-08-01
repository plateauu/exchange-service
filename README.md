### Application

##### Requirements
Source code is prepared to work correctly with Java 11 or higher. Tested on `11.0.7.hs-adpt` and `14.0.1.j9-adpt` jdk versions.

##### Compilation:   
 Command `./compile.sh` will create new fat-jar at root path of the project.
 
##### Deployment:  
 Command `./deploy.sh` will compile application if fat-jar does not exist and will run an embedded server at default port `8989`. 
 There is a possibility to override default port using: `./deploy.sh NEW_PORT_NUMBER`

##### Swagger
You can find Swagger-UI at `/swagger-ui/#/default/` endpoint.

### Nice to add later
1. Security - there is no security filters at all
2. Implement fallback method (circuit breaker) to operate properly when external service will not respond
3. Maybe we can add a kind of connection interval to NBP service to minimize number of rating requests
4. Add more reactive code (async http and rx endpoints) 
