package com.diogosson.agendamento_notificacao_api.business;

import com.diogosson.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.diogosson.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import com.diogosson.agendamento_notificacao_api.infrastructure.repositories.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final IAgendamentoMapper agendamentoMapper;

    public AgendamentoRecordOut gravarAgendamento(AgendamentoRecordIn agendamentoRecordIn) {
        Agendamento agendamento = agendamentoMapper.paraEntity(agendamentoRecordIn);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return agendamentoMapper.paraOut(agendamentoSalvo);
    }

}
