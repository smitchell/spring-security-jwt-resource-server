# SPRING SECURITY POC

This project demonstrates running multiple authentication providers. 
For example, a company might use both Active Directory and LDAP.
In this case, there are two authentication providers: one is for internal users and the other is external users.
Both providers in this project have a single, hard-coded user:

* *INTERNAL* - internalUser:pass
* *EXTERNAL* - externalUser:pass

## REST Controller

The rest controller in the Authentication Service is just for testing.
Switch to the authentication-service directory and run the tests with Maven.

```
cd authentication-service
mvn clean test
```

There are two endpoints, one for internal users and one for external users.
Note that access is restricted to the roles "ROLE_INTERNAL" and "ROLE_EXTERNAL". This is tested in the unit tests.

```
@RestController
public class MultipleAuthController {

    @PreAuthorize("hasRole('ROLE_INTERNAL')")
    @GetMapping("/api/message")
    public String getMessage() {
        for (GrantedAuthority authority :
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_INTERNAL') or hasRole('ROLE_MEMBER')")
    @GetMapping("/api/member/message")
    public String getMemberMessage() {
        for (GrantedAuthority authority :
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
        return "Hello member";
    }

}
```

# REFERENCES
* https://github.com/spring-guides/gs-authenticating-ldap
* https://www.baeldung.com/spring-security-multiple-auth-providers	
* https://memorynotfound.com/spring-boot-spring-ldap-integration-testing-example/
