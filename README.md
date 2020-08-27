# sam-backend



### Azure openjdk 11 Download
https://repos.azul.com/azure-only/zulu/packages/zulu-11/11.0.3/zulu-11-azure-jdk_11.31.11-11.0.3-win_x64.msi

### Maven build
```
mvn clean package -Dmaven.test.skip=true
```

### Docker build & run
```
docker build --tag sam-backend:1.0 .
```

```
docker run --rm -p 8080:8080 --name sam-backend  sam-backend:1.0
```

### API Test
```
http://localhost:8080/swagger-ui.html
```


### etc
```
mvn install dockerfile:build
```

```
mvn install dockerfile:push
```

