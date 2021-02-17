*HOW TO RUN THIS PROJECT*

mvn spring-boot:run

*HOW TO TEST*

curl --user proxy-client:client-secret \
     http://localhost:5000/oauth/token \
     -d 'grant_type=password&client_id=proxy-client&username=john&password=demoPa55'
     

     
     
 curl --user proxy-client:client-secret \
      http://localhost:5000/oauth/authorize \
      -d 'response_type=code&client_id=gateway-client&state=1234'

