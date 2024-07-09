## Java 21 + MinIO + Micronaut


## Requirements 

- Java 21
- Docker (running)

## Instructions

Run MinIO docker below:

```
docker run -p 9000:9000 -p 9001:9001 quay.io/minio/minio server /data --console-address ":9001"
```

## Running

```
gradle run
```

## Test
Use ```requests.http``` inside resources

Create bucket > upload > download 
