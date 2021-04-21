# hubsmsa-backend

Projeto com os módulos(camadas) que compõem o sistema.

## Iniciar

As instruções abaixo vão demonstrar alguns pontos importantes para o desenvolvimento do projeto.

### Pré-requisitos

* JDK 8u181;
* Wildfly 14.x
* Maven 3.x.;
* Docker Engine Community 19.x;

## Configurando o ambiente
### Instalar PostgreSQL via Docker

 Certifique-se que a porta 5432 esta liberada.
 
 Este comando **INSTALA** e **INICIA** o banco de dados via docker.
 
**Comando**:
 
```
cd db-docker/hubsmsa
docker-compose -f docker-compose-db.yml up --build -d
```

Quando desejar **PARAR** o banco de dados:

```
docker stop postgresql-hubsmsa
```

Quando desejar **INICIAR** o banco de dados:

```
docker start postgresql-hubsmsa
```

**Usuário BD Aplicação**:

```
Host: localhost
Port: 5432
Database: hubsmsa
Schema: hubsmsa
Username: hubsmsa
Password: hubsmsa
```

**Usuário BD Administrador:**

```
Username: postgres
Password: sonda@123
```

### Build via Maven (Liquibase + Maven Build)

Para gerenciamento das dependências e build do projeto utilizamos o Maven.

[Maven](https://maven.apache.org/) - Gerenciador de dependências e build do projeto.

Para gerenciamento dos scripts de instruções sql do projeto utilizamos Liquibase.

[Liquibase](https://www.liquibase.org/) - Rastreia, versiona, e implanta alterações de banco de dados do projeto.

* Ambiente de DEV usamos liquibase como plugin do maven. Já está configurado no pom.xml do projeto. Portanto o comando de build ira executar os scripts no banco de dados.

O build da aplicação irá criar a tabela de exemplo e as tabelas de configurações do Quartz Scheduler.
 
**Comando**:

```
mvn -Pdev clean install -Dmaven.test.skip=true
```

### Executando testes
 Para gerar os relatórios de testes e cobertura de testes unitários localmente.

**Comando**:

```
mvn test
```

## Deployment

O implantável deverá ser adicionado ao servidor de aplicação Wildfly 14.x

#### Configuração de variável de ambiente
```
set JBOSS_HOME=<pasta-onde-esta-wildfly>
```

#### Configuração dos módulos do Wildfly

  Os modulos estão disponíveis em:
```
docker/files/zips/
```

##### Módulo Keycloak
 
 Descompactar o arquivo docker/files/zips/keycloak-wildfly-adapter-dist-6.0.1.zip.
 
 Necessário ter o unzip instalado na máquina.<br>
 Disponível em: https://sourceforge.net/projects/gnuwin32/files/unzip/5.51-1/unzip-5.51-1-bin.zip/download?use_mirror=ufpr&download=

```
unzip docker/files/zips/keycloak-wildfly-adapter-dist-6.0.1.zip -d %JBOSS_HOME%
```

##### Módulo PostgreSQL
 
 Descompactar o arquivo docker/files/zips/postgresql-module.zip

```
unzip docker/files/zips/postgresql-module.zip -d %JBOSS_HOME%
```

#### Configuração de datasources

 Arquivo de configuração standalone.xml do projeto a ser utilizado está disponível em:
  
```
docker/files/standalone.xml
```

Copoie o arquivo standalone.xml para dentro do configuration do seu jboss
```
copy /Y docker\files\standalone.xml %JBOSS_HOME%\standalone\configuration\
```

  Substitua os datasources que encontrar no arquivo standalone.xml pelos indicados a seguir.
 
#### Datasource da aplicação

```
<xa-datasource jndi-name="java:/hubsmsaDS" pool-name="hubsmsaDS" enabled="true" use-java-context="true">
	<driver>postgresql</driver>
	<xa-datasource-property name="URL">jdbc:postgresql://localhost:5432/hubsmsa?currentSchema=hubsmsa</xa-datasource-property>
	<security>
		<user-name>hubsmsa</user-name>
		<password>hubsmsa</password>
	</security>
	<validation>
		<valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
		<background-validation-millis>60000</background-validation-millis>
		<exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
		<stale-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLStaleConnectionChecker"/>
	</validation>
</xa-datasource>
```

#### Datasource do Quartz Scheduler Cluster 

```
<datasource jta="false" jndi-name="java:/hubsmsaQuartzDS" pool-name="hubsmsaQuartzDS" enabled="true">
	<connection-url>jdbc:postgresql://localhost:5432/hubsmsa?currentSchema=hubsmsa</connection-url>
	<driver>postgresql</driver>
	<pool>
      	<min-pool-size>10</min-pool-size>
		<max-pool-size>50</max-pool-size>
	</pool>
	<security>
		<user-name>hubsmsa</user-name>
		<password>hubsmsa</password>
	</security>
   <validation>
		<check-valid-connection-sql>select 1</check-valid-connection-sql>
		<background-validation>true</background-validation>
		<background-validation-millis>60000</background-validation-millis>
	</validation>
</datasource>
```
#### Configuração de drivers dos datasources
```
<driver name="postgresql" module="org.postgresql">
	<xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
</driver>
```
#### Configuração de keycloak para autenticação

Altere a tag a seguir dentro do arquivo standalone.xml, com o valor da secret do projeto. Insira o valor **SEM ASPAS**. Somente altere os itens com a palavra "insira-aqui".

```
<credential name="secret">adicionar-aqui-a-secret</credential>
```

Altere os binds que serão injetados pela aplicação para uso do keycloak. Os valores adicionados **DEVEM ESTAR ENTRE ASPAS DUPLAS**. Somente altere os itens com a palavra "insira-aqui".

```
<subsystem xmlns="urn:jboss:domain:naming:2.0">
	<bindings>
		<simple name="java:/hubsmsa/params/secretJwt" value="insira-aqui-a-secret"/>
		<simple name="java:/hubsmsa/params/clientId" value="hubsmsa"/>
		<simple name="java:/hubsmsa/params/redirectUri" value="insira-aqui-o-endereço-da-aplicação(ex.: localhost:4200)"/>
		<simple name="java:/hubsmsa/params/realm" value="PBH"/>				
	</bindings>
	<remote-naming/>
</subsystem>
```

## Versões

1.0.0 - Versão inicial
