package com.app.sisos.domain

import ch.qos.logback.core.model.conditional.IfModel
import com.sun.source.tree.IfTree
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.aspectj.weaver.bcel.BcelAccessForInlineMunger
import org.aspectj.weaver.patterns.IfPointcut
import org.aspectj.weaver.patterns.PerFromSuper
import org.hibernate.boot.model.source.internal.hbm.CompositeIdentifierSingularAttributeSourceBasicImpl
import org.hibernate.dialect.function.CoalesceIfnullEmulation

@Entity
data class Secretaria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 80)
    val secretaria: String,

    @Column(nullable = false, length = 10)
    val acronimo: String
)

@Entity
data class Cliente(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 5)
    val matricula: String,

    @Column(nullable = false, length = 80)
    val nome: String
)

@Entity
data class TipoServico(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 50)
    val tipo: String,

    @Column(nullable = false, length = 250)
    val descricao: String

)

/**
 * Para os verbos http patch
 */
data class PatchSecretaria(
    val acronimo: String?,
    val secretaria: String?
)

data class PatchCliente(
    val matricula: String?,
    val nome: String?
)

data class PatchTipoServico(
    val tipo: String?,
    val descricao: String?
)