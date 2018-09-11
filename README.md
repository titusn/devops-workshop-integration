# devops-workshop-integration

This is a simple project for the DevOps integration workshop. It contains a web api which can receive an image and scan
its exif information.

## Endpoint: Ping
```http request
GET http://localhost:8080/ping

# Response:
{
  "OK": true
}
```

## Endpoint: Scan

```http request
POST http://localhost:8080/scan

<IMAGE DATA>

# Response:
{
    "fieldA": "value",
    "fieldB": "value",
    ...
}
```

## Postman

You can find a postman collection in the root of this repository
