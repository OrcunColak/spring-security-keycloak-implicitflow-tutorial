# Read me

This example does not work. The Swagger can not authorize with "Implicit Flow"

The original idea is from  
https://lejdiprifti.medium.com/secure-your-application-with-spring-security-and-keycloak-1f13f2d783cf

This demonstration shows Client authentication + Implicit flow
The client receives an access token directly from the authorization server after the user authenticates, without the
need for a client secret.

In our application, we configure OpenAPI page to automatically redirect to the Keycloak login page for authentication.

# Keycloak

http://localhost:7080

Login as admin/admin

Step 1
Create a realm named medium. Then create a client with the client id medium-app.

Step 2
We select Client authentication and in addition to the default values, we select also the Implicit flow as an
authentication flow. The Implicit Flow is suitable for client-side applications, such as single-page applications (
SPAs), where it’s not safe to store secrets because the client code is easily accessible to users. The client receives
an access token directly from the authorization server after the user authenticates, without the need for a client
secret. This eliminates the risk of the client secret being exposed in client-side code. In our application, we
configure OpenAPI page to automatically redirect to the Keycloak login page for authentication.

Step 3
We set http://localhost:8080/* as a valid redirect URI.

# Swagger

http://localhost:8080/api/ui/index.html