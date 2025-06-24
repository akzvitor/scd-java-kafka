# Plataforma de Comércio Eletrônico - Mensageria com Java e Apache Kafka

## Passo a passo para execução

## Escalabilidade

## Tolerância à falha

## Idempotência

## Explicação dos serviços

Aluno: Vitor Paulo Eterno Godoi - 202201718

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