package com.app.sisos.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SecretariaRepository : JpaRepository<Secretaria, Long> {

    @Query("FROM Secretaria WHERE secretaria LIKE %:secretaria%")
    fun findSecretaria(@Param("secretaria") secretaria: String) : List<Secretaria>

}

interface ClienteRepository : JpaRepository<Cliente, Long> {

    @Query("FROM Cliente WHERE matricula = :matricula")
    fun findMatricula(@Param("matricula") matricula : String) : List<Cliente>

    @Query("FROM Cliente WHERE nome LIKE %:nome%")
    fun findNome(@Param("nome") nome : String) : List<Cliente>

}

interface TipoServicoRepository : JpaRepository<TipoServico, Long> {

    @Query("FROM TipoServico WHERE tipo LIKE %:tipo%")
    fun findServico(@Param("tipo") tipo : String) : List<TipoServico>

}