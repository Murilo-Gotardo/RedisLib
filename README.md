
# RedisLib

**RedisLib** é uma biblioteca para [redis](https://redis.io/) leve e simples de usar — porém muito poderoso — foi criada para facilitar no desenvolvimento de aplicações que utilizam o **Redis**.


## Referência

 - [Redis](https://redis.io/)
 - [Overengineering](https://en.wikipedia.org/wiki/Overengineering)
 - [💋](https://pt.wikipedia.org/wiki/Princ%C3%ADpio_KISS)


## Uso/Exemplos

```java
import br.com.rabbithole.RedisLib;
import br.com.rabbithole.configurations.RedisConfig;
import br.com.rabbithole.core.builder.Query;
import br.com.rabbithole.core.builder.commands.generics.Get;
import br.com.rabbithole.core.builder.commands.generics.Set;
import br.com.rabbithole.core.builder.options.SetOptions;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //Inicia a Conexão com o Redis passando os parâmetros de conexão 
        //RedisConfig(IP, Porta, Usuário, Senha, Número de Conexões)
        RedisLib.init(new RedisConfig("localhost", 6379, "user", "password", 100));

        //Uma Get Query simples.
        Query<Get> getQuery = new Get.Builder()
                .setKey("Foo")
                .build();

        //Execução da Query após a construção.
        Optional<String> resultOfGetQuery = getQuery.getCommand().execute();

        //Uma Get Query com execução na construção.
        Optional<String> getQueryWithExecute = new Get.Builder()
                .setKey("Foo")
                .execute();

        //Uma Set Query simples.
        Query<Set> setQuery = new Set.Builder()
                .setKey("Foo")
                .setValue("Bar")
                .build();

        //Uma Set Query com Opções.
        Query<Set> setQueryWithOptions = new Set.Builder()
                .setKey("Foo")
                .setValue("Bar")
                .setOptions(new SetOptions.Builder()
                        .setExpire(100)
                        .setIfNotExists()
                        .setIfExists()
                        .setGet()
                ).build();
    }
}
```


## Funcionalidades



## Instalação

1. Adicione o repositório ao seu projeto.

**Maven**:
```xml
<repository>
  <id>rabbithole-repo-snapshots</id>
  <name>Rabbit Hole</name>
  <url>https://repo.rabbithole.com.br/snapshots</url>
</repository>
```

**Gradle(Groovy)**:
```groovy
maven {
    url "https://repo.rabbithole.com.br/snapshots"
}
```

**Gradle(Kotlin)**:
```kotlin
maven {
    url = uri("https://repo.rabbithole.com.br/snapshots")
}
```
2. Adicione a dependência.

**Maven**:
```xml
<dependency>
  <groupId>br.com.rabbithole</groupId>
  <artifactId>RedisLib</artifactId>
  <version>2.0.0-SNAPSHOT</version>
</dependency>
```

**Gradle(Groovy)**:
```groovy
implementation "br.com.rabbithole:RedisLib:2.0.0-SNAPSHOT"
```

**Gradle(Kotlin)**:
```kotlin
implementation("br.com.rabbithole:RedisLib:0.0.0-SNAPSHOT")
```
## Licença

[MIT](https://choosealicense.com/licenses/mit/)

