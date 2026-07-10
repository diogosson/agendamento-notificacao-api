package com.diogosson.agendamento_notificacao_api.controller.dto;

import com.diogosson.agendamento_notificacao_api.business.AgendamentoService;
import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
