package com.diogosson.agendamento_notificacao_api.service;

import com.diogosson.agendamento_notificacao_api.business.AgendamentoService;
import com.diogosson.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.diogosson.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import com.diogosson.agendamento_notificacao_api.infrastructure.exceptions.NotFoundException;
import com.diogosson.agendamento_notificacao_api.infrastructure.repositories.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.diogosson.agendamento_notificacao_api.infrastructure.enums.StatusNotificacaoEnum.AGENDADO;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;
    @Mock
    private IAgendamentoMapper agendamentoMapper;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgendamentoRecordIn agendamentoRecordIn;
    private AgendamentoRecordOut agendamentoRecordOut;
    private Agendamento agendamento;

    @BeforeEach
    void setUp() {

        this.agendamentoRecordIn = new AgendamentoRecordIn(
                "email@email.com",
                "00911112222",
                "Retorna a loja com urgência",
                of(2026, 7, 20, 12, 30, 0)
        );

        this.agendamentoRecordOut = new AgendamentoRecordOut(
                1L,
                "email@email.com",
                "00911112222",
                "Retorna a loja com urgência",
                of(2026, 7, 20, 12, 30, 0),
                AGENDADO
        );

        this.agendamento = new Agendamento(
                1L,
                "email@email.com",
                "00911112222",
                of(2026, 7, 20, 12, 30, 0),
                now(),
                null,
                "Retorna a loja com urgência",
                AGENDADO
        );
    }

    @Test
    void deveGravarAgendamentoComSucesso() {

        when(agendamentoMapper.paraEntity(agendamentoRecordIn)).thenReturn(agendamento);
        when(agendamentoMapper.paraOut(agendamento)).thenReturn(agendamentoRecordOut);
        when(agendamentoRepository.save(agendamento)).thenReturn(agendamento);

        AgendamentoRecordOut out = agendamentoService.gravarAgendamento(agendamentoRecordIn);

        verify(agendamentoMapper, times(1)).paraOut(agendamento);
        verify(agendamentoMapper, times(1)).paraEntity(agendamentoRecordIn);
        verify(agendamentoRepository, times(1)).save(agendamento);

        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoRecordOut);
    }

    @Test
    void buscarAgendamentoPorIdComSucesso() {

        when(agendamentoMapper.paraOut(agendamento)).thenReturn(agendamentoRecordOut);
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));

        AgendamentoRecordOut out = agendamentoService.buscarAgendamentoPorId(1L);

        verify(agendamentoMapper, times(1)).paraOut(agendamento);
        verify(agendamentoRepository, times(1)).findById(1L);

        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoRecordOut);
    }

    @Test
    void buscarAgendamentoPorIdComFalha() {

        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> agendamentoService.buscarAgendamentoPorId(1L)
        );

        assertEquals("Id não encontrado", exception.getMessage());

        verify(agendamentoMapper, never()).paraOut(agendamento);
        verify(agendamentoRepository, times(1)).findById(1L);
    }

    @Test
    void cancelarAgendamentoPorIdComSucesso() {

        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));
        when(agendamentoMapper.paraEntityCancelamento(agendamento)).thenReturn(agendamento);
        when(agendamentoRepository.save(agendamento)).thenReturn(agendamento);

        agendamentoService.cancelarAgendamentoPorId(1L);

        verify(agendamentoMapper, times(1)).paraEntityCancelamento(agendamento);
        verify(agendamentoRepository, times(1)).findById(1L);
        verify(agendamentoRepository, times(1)).save(agendamento);
    }

    @Test
    void cancelarAgendamentoPorIdComFalha() {

        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> agendamentoService.cancelarAgendamentoPorId(1L)
        );

        assertEquals("Id não encontrado", exception.getMessage());

        verify(agendamentoMapper, never()).paraEntityCancelamento(agendamento);
        verify(agendamentoRepository, times(1)).findById(1L);
        verify(agendamentoRepository, never()).save(agendamento);
    }
}
