# 🛒 Marketplace Microservices API

Bem-vindo ao repositório do projeto **Marketplace Microservices**. Este é um sistema distribuído desenvolvido para simular um ambiente real de e-commerce, focado em escalabilidade, resiliência e boas práticas de arquitetura de software.

## 🚀 Sobre o Projeto

Este projeto consiste em um ecossistema de microsserviços que gerencia o fluxo de um marketplace, desde o cadastro de produtos até o processamento de pedidos e notificações assíncronas.

**O objetivo principal** é demonstrar a aplicação de padrões de arquitetura modernos utilizando o ecossistema Spring e orquestração de containers.

---

## 🏗️ Arquitetura e Tecnologias

A solução foi desenhada seguindo a arquitetura de **Microsserviços**, onde cada serviço tem responsabilidade única e se comunica via APIs REST e mensageria assíncrona.

### Stack Tecnológica:
* **Linguagem:** Java 17+
* **Framework Principal:** Spring Boot 3.x
* **Comunicação Assíncrona:** RabbitMQ (ou Kafka, conforme sua escolha)
* **Banco de Dados:** PostgreSQL / MongoDB (um por microsserviço)
* **Containerização:** Docker & Docker Compose
* **Orquestração:** Kubernetes (K8s)
* **Discovery & Gateway:** Spring Cloud Netflix Eureka & API Gateway

### 🧩 Serviços do Sistema:
1.  **Product-Service:** Gerencia o catálogo de produtos.
2.  **Order-Service:** Processa os pedidos de compra.
3.  **Payment-Service:** Simula a aprovação de pagamentos.
4.  **Notification-Service:** Consome mensagens da fila para enviar e-mails/notificações de status.

---

## ⚙️ Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:
* [Java JDK 17](https://www.oracle.com/java/technologies/downloads/)
* [Docker](https://www.docker.com/) & Docker Compose
* [Maven](https://maven.apache.org/) (opcional, se usar o wrapper)
* Minikube ou Kind (para testes locais com Kubernetes)

---

## 🏃‍♂️ Como Rodar o Projeto

### Opção 1: Via Docker Compose (Mais rápido para Dev)
Esta opção sobe toda a infraestrutura (bancos, RabbitMQ e apps) com um único comando.

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/marketplace-microservices.git](https://github.com/seu-usuario/marketplace-microservices.git)
   cd marketplace-microservices
