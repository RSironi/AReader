# AReader

TCC - AReader

API REST desenvolvida para o TCC do projeto AReader (visualização em realidade aumentada de anotações virtuais em livros). 

Desenvolvido com:
- Java 17
- Spring Boot 3
  - Web
  - JPA
  - Security
  - Validation
  - REST Docs
- Google Cloud SQL
  - PostgreSQL

  Utiliza/Trabalha com:
  - Design Pattern MVC
  - Convenções de nomenclatura
  - Testes Automatizados (não utilizam o bd de Produção)
  - H2 Database (utilizado nos testes)
  - Multipart File
  - Java Optional
  - Lombok
  - SOLID
  - Docker
  - Apache HTTP (para comunicação com API da IA)
  - Google Credentials
  - Handler básico de Erros

## Uso
**Necessário editar as configurações com suas próprias credenciais e acessos.**

Via java
```bash
#execute o arquivo na IDE ./src/main/java/com/tcc/AReader
AReaderApplication.java
```

Via docker
```yml
#execute o dockerfile
docker build -t areader . #gera o .jar da aplicação via maven + java e configura para inicializar .jar

#execute a imagem gerada
#porta default do Spring -> port = 8080
docker run -p 8080:8080 IMAGE
```

## Digrama de classes todo o backend Spring
![diagrama de classes todo o Backend](https://github.com/RSironi/AReader/assets/96712987/180dbb71-d88f-4d0e-91b7-57fbf6e00f01)


>Spring Docs = APLICAÇÃO/swagger-ui

>Questões de segurança da aplicação e repositório estão sendo tratadas.
