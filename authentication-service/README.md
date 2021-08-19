*HOW TO RUN THIS PROJECT*

mvn spring-boot:run

*STEPS TO TEST WITH CURL*


1 - Paste this is a browser to get the auth code:

Start the project:

```
mvn spring-boot:run
```

Open a web browser and paste this URL:
     
[http://localhost:8090/oauth/authorize?response_type=code&client_id=gateway-client&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized](http://localhost:8090/oauth/authorize?response_type=code&client_id=gateway-client&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized)

You will be prompted to log in, and then the returned redirect URL will contain the "code" query parameter. Copy the code to the clipboard.    

2 - Exchange the auth code for a token

Export the client credentials and the auth code copied above.

```
export CLIENT_ID=gateway-client
export CLIENT_SECRET=client-secret

export AUTH_CODE=[YOUR CODE]
```

Run this curl command:

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
--user "$CLIENT_ID:$CLIENT_SECRET" \
-d "grant_type=authorization_code&code=$AUTH_CODE&client_id=gateway-client&client_secret=client-secret&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized" \
http://localhost:8090/oauth/token
```

You will receive a JWT token. Export its refresh_token value.

```
export REFRESH_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImV4YW1wbGUta2V5LWlkIn0.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsidHJ1c3QiLCJyZWFkIiwid3JpdGUiXSwiYXRpIjoiY2Y1ZmRjMTctZmQxMC00Y2Q4LWIzNWEtNjAxMWU4NjI3ZWQ2IiwiZXhwIjoxNjI5NDgwNTI0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0VNUExPWUVFIl0sImp0aSI6IjRiZjIyZjU1LTVjODMtNDFlMi05ZjE4LWZmNTQ0ZTc0M2IwNCIsImNsaWVudF9pZCI6ImdhdGV3YXktY2xpZW50In0.MUAmBHmqqDWcFtsBkNrvmJbFbP4qumvwF0gi6FgWbDGouHKF4blV62Lt0L-STwPcCuM7eLXRNhZ3Q2v7uNwT7gIcC6cXqWqoYyqxaPri-utJyFPDAw90qPoch6WzFcYzMxbGN9kBf6AyuvsyLxAv_kD75SSy3IX7JBw6uBbDVnd4kS2FCKv8rCt7-aTQIgpNk5FfWfnk5KNQ76cWaHyNCMGJT-joeVlT6LM5fLC3oywUJUTf_fENpsh9cZQ8Y2qAAX99JSEU9jPRKHJYLlDN-1UHkbzp0a9QRBZic-WwcPHdPI9A3tAcRsd596jG4e51m3ojUzuViqENzlUBUElDlw
```

3 - Use Refresh Token to obtain a long-lived access token.

```
curl --user "$CLIENT_ID:$CLIENT_SECRET" \
  -X POST http://localhost:8090/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=refresh_token&scope=write&scope=read&refresh_token=$REFRESH_TOKEN"
```

You should receive a new JWT token, such as this one.

```
{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImV4YW1wbGUta2V5LWlkIn0.eyJleHAiOjE2MjkzOTgwMjgsInVzZXJfbmFtZSI6ImpvaG4iLCJqdGkiOiIwYzE5YTU4ZS1kNGI5LTQwYzAtYjAxNy0wZGNkYzlhY2MzYjMiLCJjbGllbnRfaWQiOiJnYXRld2F5LWNsaWVudCIsInNjb3BlIjpbIndyaXRlIl19.zI1mWdJ7VSpY4IJI6i9WBI9FAH9jSIKOyifxDo0ku_t2A9jw5VJqSO5PXM4PEQTUilRqmNpZHtHBMwnwpmsbFbzer8U2lzjKxyvMqccLeCfxVwoD3F5HpUlD95w0NxNcCGRH5kNG7ZuhLVGnW59XmCbs0zypKEmgzWPhtJXmP68_TRW5mA4RFqihzO7o6oxRJU4F2iA4OKEseBClrj7vtPbnz-ZX1Etq9Ai7dplD8IBfa8lE6HpIlEPtwbwJFXgGU17xBsqoJH596sdOBWxlvLyJtbJVsJOcDzv0sTZXS5oZrJ5fz71TuXorEnftUBPlRNgijt44t41aDegGEM5Zfw","token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImV4YW1wbGUta2V5LWlkIn0.eyJleHAiOjE2Mjk0ODA1MjQsInVzZXJfbmFtZSI6ImpvaG4iLCJqdGkiOiI0YmYyMmY1NS01YzgzLTQxZTItOWYxOC1mZjU0NGU3NDNiMDQiLCJjbGllbnRfaWQiOiJnYXRld2F5LWNsaWVudCIsInNjb3BlIjpbIndyaXRlIl0sImF0aSI6IjBjMTlhNThlLWQ0YjktNDBjMC1iMDE3LTBkY2RjOWFjYzNiMyJ9.XZ7zH9rBFTyQKNMJNg3sdA6D-RWqrrSSMEsYD6Q6-xK1rzXZyo4hpyHhTLJmvlsnp_YBEbE8JjHBsjFlzacUh1_FwyqzYqKRgiTgWTSUExCCK-fZbjDrdQGNNN-M5HwKnMOZBZ5lGIXhIJNbl1Vpz7sQzPDpqYpRT2lBh_ZfQc-HxOxNSjRRg_0Mx1w-D_-F_G9QIT18KKjTBqE2Vj1X_3bzFYbLoTXbRmVL9qbEcMeaAhA-HluD9Pzxs4ZUt5JenC61qBVhoYSVl9CbZZ7Y5GIIENC-qhy_oNJIshkzzq3Q08yov3aWiFnL7wd6TZNC5ssLjv1w4FYRlpRxSNr4vg","expires_in":3599,"scope":"write","jti":"0c19a58e-d4b9-40c0-b017-0dcdc9acc3b3"}
```
