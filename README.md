# 📊 FinancialTracker (EM ANDAMENTO)

O **FinancialTracker** é um sistema de controle financeiro pessoal desenvolvido com o objetivo de aprender e aplicar os principais conceitos do ecossistema **Spring Boot**, incluindo autenticação com **JWT**, estruturação de APIs REST, boas práticas de organização de código e separação de responsabilidades.

## 🎯 Objetivo do Projeto

A proposta do FinancialTracker é criar uma aplicação realista e utilizável para registrar, categorizar e acompanhar transações financeiras, servindo como uma base sólida para estudar:

- Desenvolvimento backend com Java moderno
- Conceitos de segurança com autenticação baseada em tokens
- Estruturação de projetos com camadas bem definidas
- Integração futura com banco de dados, dashboards, e interfaces

Este projeto não nasceu apenas como um experimento técnico, mas como uma aplicação que poderia de fato evoluir para algo útil, com foco em **clareza**, **boas práticas** e **aprendizado contínuo**.

## 🧱 Estrutura e Organização

O projeto segue a estrutura típica de aplicações Spring Boot, com as responsabilidades separadas entre pacotes como `controller`, `service`, `repository`, `model` e `security`. Isso facilita a manutenção e a escalabilidade do sistema à medida que novas funcionalidades forem adicionadas.

## 🔐 Autenticação

A autenticação é baseada em **JWT**, uma abordagem moderna que permite controlar o acesso a diferentes partes da aplicação de forma segura e sem estado. A geração e validação de tokens é feita manualmente, com o intuito de compreender cada etapa do processo e evitar soluções prontas sem entendimento.

## 💡 Motivação

Muitos tutoriais ensinam a usar Spring Boot de forma superficial ou apenas com exemplos artificiais. Este projeto foi criado como um exercício prático e incremental, em que cada nova etapa resolve um problema real e traz uma lição útil sobre o funcionamento do Spring e do Java no backend.

A ideia é que, ao final, o projeto sirva tanto como ferramenta quanto como portfólio de aprendizado sólido.

## 📌 Próximos Passos

- Finalizar CRUD de transações
- Conectar com banco de dados (PostgreSQL)
- Criar autenticação com cadastro de usuário
- Proteger rotas sensíveis com Spring Security
- Adicionar geração de relatórios e gráficos
- Deploy em ambiente online com Railway

---

Este projeto é uma base em constante construção, pensada para aprender fazendo e documentar o processo de aprendizado com código limpo e decisões técnicas conscientes.
