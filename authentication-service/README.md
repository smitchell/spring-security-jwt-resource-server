*HOW TO RUN THIS PROJECT*

mvn spring-boot:run

*HOW TO TEST*

curl --user proxy-client:client-secret \
     http://localhost:5000/oauth/token \
     -d 'grant_type=password&client_id=proxy-client&username=john&password=demoPa55'

