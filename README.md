# 🛠️ Sistema de Inventário e Planejamento de Produção (Autoflex Test)

Este projeto é uma aplicação Full-Stack desenvolvida para o desafio técnico da Autoflex. O objetivo principal é gerenciar o estoque de matérias-primas e sugerir um plano de produção inteligente baseado na disponibilidade de recursos e no valor de mercado dos produtos.

## 📝 Funcionalidades Principais

* **Gestão de Insumos:** Cadastro, edição e exclusão de matérias-primas com controle de estoque.
* **Composição de Produtos:** Definição de receitas de produtos, vinculando múltiplos materiais a um item final.
* **Cálculo de Produção (RF004):** O sistema calcula automaticamente quantas unidades de cada produto podem ser fabricadas com o estoque atual.
* **Priorização por Valor:** O plano de produção sugere primeiro os produtos de maior valor unitário para maximizar o faturamento.
* **Alertas de Escassez:** Caso um produto não possa ser fabricado, o sistema indica exatamente qual material está em falta através de um alerta visual.

## 💻 Stack Tecnológica

### Back-end
* **Java 17** com **Spring Boot 3**.
* **Spring Data JPA**: Para persistência de dados e mapeamento objeto-relacional.
* **PostgreSQL**: Banco de dados relacional.
* **JUnit & Mockito**: Testes automatizados para validar a lógica do plano de produção.

### Front-end
* **React.js**: Construção da interface de usuário com Hooks (`useState`, `useEffect`).
* **Bootstrap**: Estilização e componentes visuais responsivos.
* **Axios**: Integração entre o Front-end e a API REST.

---

## 🏗️ Arquitetura do Projeto

O sistema foi desenvolvido seguindo o padrão **Monolítico**, garantindo uma comunicação direta entre os serviços e facilitando o gerenciamento da integridade referencial do banco de dados (uso de `Cascade` para exclusões seguras).



---

## 🚀 Como Executar o Projeto

### 1. Requisitos
* Java JDK 17+.
* Node.js & npm.
* PostgreSQL rodando localmente.

### 2. Configuração do Banco de Dados
Ajuste as credenciais no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco

### 3. Rodando o Back-end
Bash
./mvnw spring-boot:run
4. Rodando o Front-end
Bash
# Entre na pasta do frontend
npm install
npm start
🧪 Testes Automatizados
Eu implementei testes unitários para garantir que o cálculo do plano de produção (quantidade produzível e valor total) esteja sempre correto:

Bash
./mvnw test
Desenvolvido como parte do processo seletivo para Desenvolvedor na Autoflex - 2026.


---

### Dicas para o GitHub:
1.  **Imagens:** Se você tirou prints da tela funcionando (como a tabela com o alerta de falta de material), você pode criar uma pasta chamada `img` no seu repositório, subir as fotos lá e linkar no README usando `![Descrição](img/sua-foto.png)`.
2.  **Sinceridade:** Esse README mostra que você entende o que é um **Monolito** e que sabe usar ferramentas de teste como **Mockito**.

**Gostaria que eu revisasse mais algum ponto antes de você finalizar o envio do test
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
