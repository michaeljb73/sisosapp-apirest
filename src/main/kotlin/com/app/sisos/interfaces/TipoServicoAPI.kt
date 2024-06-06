package com.app.sisos.interfaces

import com.app.sisos.domain.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class TipoServicoAPI(val tipoServicoRepository: TipoServicoRepository) {

    @GetMapping("/servicos")
    fun listServicos() = tipoServicoRepository.findAll()

    @GetMapping("/servicos/{id}")
    fun findServico(@PathVariable("id") id: Long) =
        tipoServicoRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    /**
     * Esta requisição retorna uma lista vazia caso não encontre algo.
     */
    @GetMapping("/servico")
    fun findServicoByNome(@RequestParam(value="nome") nome: String) =
        tipoServicoRepository.findServico(nome)

    @PostMapping("/servicos")
    fun createServico( @RequestBody tipoServico: TipoServico ): TipoServico =
        tipoServicoRepository.save(tipoServico)

    @PutMapping("/servicos/{id}")
    fun fullUpdateServico(
        @PathVariable("id") id: Long,
        @RequestBody tipoServico: TipoServico
    ):
            TipoServico {
        val foundServico = findServico(id)
        val copyServico = foundServico.copy(
            tipo = tipoServico.tipo,
            descricao = tipoServico.descricao
        )
        return tipoServicoRepository.save(copyServico)
    }

    @PatchMapping("/servicos/{id}")
    fun incrementalUpdateServico(
        @PathVariable("id") id: Long,
        @RequestBody tipoServico: PatchTipoServico
    ):
            TipoServico {
        val foundServico = findServico(id)
        val copyServico = foundServico.copy(
            tipo = tipoServico.tipo ?: foundServico.tipo,
            descricao = tipoServico.descricao ?: foundServico.descricao
        )
        return tipoServicoRepository.save(copyServico)
    }

    @DeleteMapping("/servicos/{id}")
    fun deleteServico(@PathVariable("id") id: Long) {
        // val foundServico = findServico(id)
        tipoServicoRepository.delete(findServico(id))
    }

}