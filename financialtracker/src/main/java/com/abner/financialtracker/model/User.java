package com.abner.financialtracker.model;

// Define que um campo da classe será mapeado para uma coluna da tabela no banco de dados.
import jakarta.persistence.Column;

// Indica que a classe representa uma entidade JPA, ou seja, uma tabela no banco de dados.
import jakarta.persistence.Entity;

// Especifica que um campo do tipo enum deve ser armazenado como STRING ou ORDINAL no banco de dados.
import jakarta.persistence.EnumType;

// Usado junto com @Enumerated para informar o tipo de mapeamento do enum (STRING ou ORDINAL).
import jakarta.persistence.Enumerated;

// Indica que o valor do campo será gerado automaticamente (ex: auto-incremento para chaves primárias).
import jakarta.persistence.GeneratedValue;

// Define a estratégia para geração automática de valores (ex: IDENTITY, AUTO, SEQUENCE, TABLE).
import jakarta.persistence.GenerationType;

// Indica o campo que é a chave primária da entidade (a coluna que identifica unicamente cada registro).
import jakarta.persistence.Id;

// Mapeia a entidade para uma tabela específica do banco de dados (caso queira definir o nome da tabela explicitamente).
import jakarta.persistence.Table;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserEntity")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public enum Role {
        USER,
        ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

}