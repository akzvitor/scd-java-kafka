# Plataforma de Comércio Eletrônico - Mensageria com Java e Apache Kafka
Aluno: Vitor Paulo Eterno Godoi - 202201718

Curso: Engenharia de Software - UFG
Disciplina: Software Concorrente e Distribuído
Professor: Elias Batista Ferreira

## Visão geral

## Passo a passo para execução
### Requisitos
- Java
- Maven
- Docker

### Criação dos tópicos kafka

1 - Iniciar os containers do kafka, zookeper e postgres pelo docker-compose, executando o seguinte comando na pasta raiz do projeto (padrão scd-java-kafka):

```bash
docker-compose up -d
```

Esse comando vai baixar as imagens necessárias e iniciar os containers, sendo possível executar os comandos de criação dos tópicos do
kafka e persistir dados no banco com o postgres.

2 - Criar os tópicos necessários, executando:

```bash
docker exec -it kafka-scd-jkm kafka-topics --create --topic orders --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
em seguida

```bash
docker exec -it kafka-scd-jkm kafka-topics --create --topic inventory-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

Cada um dos comandos cria um tópico no container do kafka (kafka-scd-jkm) que está rodando, o primeiro cria o orders, que
vai registrar os pedidos a partir do serviço order-service, e ser lido pelo serviço inventory-service.

A partir da leitura de um pedido do tópico orders, o serviço inventory-service publica no tópico criado pelo segundo comando, inventory-events,
que vai ser lido pelo serviço notification-service, responsável apenas por consumir o tópico e enviar uma notificação de acordo.

3 - Instalar as dependencias do maven e executar os serviços em seus respectivos diretórios, cada um em um terminal:

```bash
cd order-service && mvn package clean && mvn spring-boot:run
```

```bash
cd inventory-service && mvn package clean && mvn spring-boot:run

```bash
cd notification-service && mvn package clean && mvn spring-boot:run
```

Esses comandos são responsáveis por iniciar cada um dos serviços do projeto. Importante: cada serviço precisa ficar
em execução durante a aplicação, então é necessário executar cada um em um terminal.

O serviço inventory-service, além de ouvir/ler o tópico order e publicar no inventory-events, também
inicializa uma tabela product com alguns produtos predefinidos, que podem ser encontrados e alterados
no caminho ./inventory-service/src/main/resources/data.sql

Para simplificar, a inicialização dos produtos é feita de uma maneira que, se já existir produtos com os ID's (code) na tabela,
não tenta adicionar novamente, já que o ID é uma PK.

4 - Visualizar banco de dados (opcional)

Para ver na prática o processamento dos pedidos, é possível visualizar o banco de dados do container
via psql, executando o comando:
```bash
docker exec -it postgres-scd-jkm psql -U vitor -d inventory
```
Após executar no terminal, será possível executar alguns comandos para visualizar e até mesmo manipular a tabela. Para informações adicionais, digite o comando `help`.

Comandos principais:
- `\dt` para visualizar as tabelas do banco;
- `SELECT * FROM product;` para visualizar os produtos da tabela product;
- `DELETE FROM product;` para deletar todos os dados da tabela product;

## Escalabilidade

## Tolerância à falha

## Idempotência

## Explicação dos serviços

## Informações adicionais - documento de definição do projeto
1. Objetivo geral
Projetar, implementar e documentar um sistema distribuído em Java que use o Apache
Kafka ou ActiveMQ como backbone de mensageria para processar eventos em tempo
real, explorando tópicos, partições, etc.

2. Cenário proposto
Você fará parte da equipe de engenharia de uma plataforma de comércio eletrônico.
    - Serviço “Order‑Service” (produtor) publica em um tópico orders todos os
    pedidos confirmados.
    - Serviço “Inventory‑Service” (consumidor + produtor) consome orders, reserva
    estoque e publica resultado em inventory-events.
    - Serviço “Notification‑Service” (consumidor) lê inventory-events e envia
    e‑mails/SMS simulados.

3. Requisitos funcionais
    - RF1 -> Criar tópicos/fila orders e inventory-events via kafka-topics.sh.
    - RF2 -> Order‑Service expõe uma REST API (POST /orders) que gera um UUID, timestamp e lista de itens.
    - RF3 -> Inventory‑Service processa mensagens em ordem e publica sucesso ou falha (sem estoque).
    - RF4 -> Notification‑Service registra no console a notificação enviada.

4. Requisitos não-funcionais
    - RNF1 -> Escalabilidade: Explique como você poderia conseguir escalabilidade com o Broker utilizado.
    - RNF2 -> Tolerância à falha : O que significa? Explique uma situação de falha que poderia ocorrer e como o Broker poderia tratá-la.
    - RNF3 -> Idempotência: Explique esse conceito e como fazer para garanti-lo.
    - RNF4 -> Documentação: README.md explicando os serviços e como fazer para executar a solução. O nome dos componentes do grupo e as respostas às questões
    anteriores também podem ser adicionadas a este arquivo.