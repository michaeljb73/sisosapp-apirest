package com.app.sisos.interfaces

import com.app.sisos.domain.ClienteRepository
import com.app.sisos.domain.PatchCliente
import com.app.sisos.domain.Cliente
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ClienteAPI(val clienteRepository: ClienteRepository) {

    @GetMapping("/clientes")
    fun listClientes() = clienteRepository.findAll()

    @GetMapping("/clientes/{id}")
    fun findCliente(@PathVariable("id") id: Long) =
        clienteRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    /**
     * Esta requisição retorna uma lista vazia caso não encontre a matrícula.
     */
    @GetMapping("/clientes/matricula")
    fun findClienteMatricula(@RequestParam(value="numero") matricula: String) =
        clienteRepository.findMatricula(matricula)

    @PostMapping("/clientes")
    fun createCliente( @RequestBody cliente: Cliente): Cliente =
        clienteRepository.save(cliente)

    @PutMapping("/clientes/{id}")
    fun fullUpdateCliente(
        @PathVariable("id") id: Long,
        @RequestBody cliente: Cliente):
            Cliente {
        val foundCliente = findCliente(id)
        val copyCliente = foundCliente.copy(
            matricula = cliente.matricula,
            nome = cliente.nome
        )
        return clienteRepository.save(copyCliente)
    }

    @PatchMapping("/clientes/{id}")
    fun fullUpdateCliente(
        @PathVariable("id") id: Long,
        @RequestBody cliente: PatchCliente):
            Cliente {
        val foundCliente = findCliente(id)
        val copyCliente = foundCliente.copy(
            matricula = cliente.matricula ?: foundCliente.matricula,
            nome = cliente.nome ?: foundCliente.nome
        )
        return clienteRepository.save(copyCliente)
    }

    @DeleteMapping("/clientes/{id}")
    fun deleteCliente(@PathVariable("id") id: Long) {
        val foundCliente = findCliente(id)
        clienteRepository.delete(findCliente(id))
    }

}