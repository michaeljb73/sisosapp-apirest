package com.app.sisos.interfaces

import com.app.sisos.domain.PatchSecretaria
import com.app.sisos.domain.Secretaria
import com.app.sisos.domain.SecretariaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Service
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class SecretariaAPI(val secretariaRepository: SecretariaRepository) {

    @GetMapping("/secretarias")
    fun listSecretarias() = secretariaRepository.findAll()

    @GetMapping("/secretarias/{id}")
    fun findSecretaria(@PathVariable("id") id: Long) =
        secretariaRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    /**
     * Esta requisição retorna uma lista vazia caso não encontre a matrícula.
     */
    @GetMapping("/secretaria")
    fun findSecretariaByNome(@RequestParam(value="nome") secretaria: String) =
        secretariaRepository.findSecretaria(secretaria)

    @PostMapping("/secretarias")
    fun createSecretaria( @RequestBody secretaria: Secretaria): Secretaria =
        secretariaRepository.save(secretaria)

    @PutMapping("/secretarias/{id}")
    fun fullUpdateSecretaria(
        @PathVariable("id") id: Long,
        @RequestBody secretaria: Secretaria):
            Secretaria {
        val foundSecretaria = findSecretaria(id)
        val copySecretaria = foundSecretaria.copy(
            acronimo = secretaria.acronimo,
            secretaria = secretaria.secretaria
        )
        return secretariaRepository.save(copySecretaria)
    }

    @PatchMapping("/secretarias/{id}")
    fun incrementalUpdateSecretaria(
        @PathVariable("id") id: Long,
        @RequestBody secretaria: PatchSecretaria):
            Secretaria {
        val foundSecretaria = findSecretaria(id)
        val copySecretaria = foundSecretaria.copy(
            acronimo = secretaria.acronimo ?: foundSecretaria.acronimo,
            secretaria = secretaria.secretaria ?: foundSecretaria.secretaria
        )
        return secretariaRepository.save(copySecretaria)
    }

    @DeleteMapping("/secretarias/{id}")
    fun deleteSecretaria(@PathVariable("id") id: Long) {
        // val foundSecretaria = findSecretaria(id)
        secretariaRepository.delete(findSecretaria(id))
    }

    /**
     * Continuar no capítulo 3:
     * CRIANDO RELACIONAMENTOS
     * ENTRE RECURSOS
     */

}