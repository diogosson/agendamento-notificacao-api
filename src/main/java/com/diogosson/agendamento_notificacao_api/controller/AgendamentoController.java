package com.diogosson.agendamento_notificacao_api.controller;

import com.diogosson.agendamento_notificacao_api.business.AgendamentoService;
import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.diogosson.agendamento_notificacao_api.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoRecordOut> gravarAgendamento(@RequestBody AgendamentoRecordIn agendamentoRecordIn) {

        AgendamentoRecordOut agendamentoRecordOut = agendamentoService.gravarAgendamento(agendamentoRecordIn);

        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoRecordOut);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoRecordOut> buscarAgendamentoPorId(@PathVariable("id") Long id){
        try {
            AgendamentoRecordOut agendamentoRecordOut = agendamentoService.buscarAgendamentoPorId(id);
            return  ResponseEntity.status(HttpStatus.OK).body(agendamentoRecordOut);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
