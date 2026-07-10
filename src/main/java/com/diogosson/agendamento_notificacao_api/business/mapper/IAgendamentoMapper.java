package com.diogosson.agendamento_notificacao_api.business.mapper;

import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.diogosson.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IAgendamentoMapper {
    Agendamento paraEntity(AgendamentoRecordIn agendamento);
    AgendamentoRecordOut paraOut(Agendamento agendamento);
}
