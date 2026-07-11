package com.diogosson.agendamento_notificacao_api.controller;

import com.diogosson.agendamento_notificacao_api.business.AgendamentoService;
import com.diogosson.agendamento_notificacao_api.controller.dto.in.AgendamentoRecordIn;
import com.diogosson.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.diogosson.agendamento_notificacao_api.infrastructure.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static com.diogosson.agendamento_notificacao_api.infrastructure.enums.StatusNotificacaoEnum.AGENDADO;
import static java.lang.String.valueOf;
import static java.time.LocalDateTime.of;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @Mock
    AgendamentoService agendamentoService;

    @InjectMocks
    AgendamentoController agendamentoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private AgendamentoRecordIn agendamentoRecordIn;
    private AgendamentoRecordOut agendamentoRecordOut;

    @BeforeEach
    void setUp() {

        this.mockMvc = standaloneSetup(agendamentoController).build();
        this.objectMapper = new ObjectMapper();

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
    }

    @Test
    void deveCriarAgendamentoComSucesso() throws Exception {
        when(agendamentoService.gravarAgendamento(agendamentoRecordIn))
                .thenReturn(agendamentoRecordOut);

        mockMvc
                .perform(post("/agendamento")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamentoRecordIn)))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.id")).value(agendamentoRecordOut.id()))
                .andExpect((jsonPath("$.emailDestinatario")).value(agendamentoRecordOut.emailDestinatario()))
                .andExpect((jsonPath("$.telefoneDestinatario")).value(agendamentoRecordOut.telefoneDestinatario()))
                .andExpect((jsonPath("$.mensagem")).value(agendamentoRecordOut.mensagem()))
                .andExpect((jsonPath("$.dataHoraEnvio")).value(agendamentoRecordOut.dataHoraEnvio().format(ofPattern("dd/MM/yyyy HH:mm:ss"))))
                .andExpect((jsonPath("$.statusNotificacao")).value(valueOf(agendamentoRecordOut.statusNotificacao())));

        verify(agendamentoService, times(1)).gravarAgendamento(agendamentoRecordIn);

    }

    @Test
    void buscarAgendamentoPorIdComSucesso() throws Exception{
        when(agendamentoService.buscarAgendamentoPorId(anyLong()))
                .thenReturn(agendamentoRecordOut);

        mockMvc
                .perform(get("/agendamento/{id}", anyLong())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.id")).value(agendamentoRecordOut.id()))
                .andExpect((jsonPath("$.emailDestinatario")).value(agendamentoRecordOut.emailDestinatario()))
                .andExpect((jsonPath("$.telefoneDestinatario")).value(agendamentoRecordOut.telefoneDestinatario()))
                .andExpect((jsonPath("$.mensagem")).value(agendamentoRecordOut.mensagem()))
                .andExpect((jsonPath("$.dataHoraEnvio")).value(agendamentoRecordOut.dataHoraEnvio().format(ofPattern("dd/MM/yyyy HH:mm:ss"))))
                .andExpect((jsonPath("$.statusNotificacao")).value(valueOf(agendamentoRecordOut.statusNotificacao())));

        verify(agendamentoService, times(1)).buscarAgendamentoPorId(anyLong());
    }

    @Test
    void buscarAgendamentoPorIdComFalha() throws Exception{
        when(agendamentoService.buscarAgendamentoPorId(anyLong()))
                .thenThrow(NotFoundException.class);

        mockMvc
                .perform(get("/agendamento/{id}", anyLong())
                        .accept(APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(""));


        verify(agendamentoService, times(1)).buscarAgendamentoPorId(anyLong());
    }

    @Test
    void cancelarAgendamentoPorIdComSucesso() throws Exception {
        doNothing().when(agendamentoService).cancelarAgendamentoPorId(anyLong());

        mockMvc
                .perform(put("/agendamento/{id}", anyLong()))
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));

        verify(agendamentoService, times(1)).cancelarAgendamentoPorId(anyLong());
    }

    @Test
    void cancelarAgendamentoPorIdComFalha() throws Exception {

        doThrow(NotFoundException.class).when(agendamentoService).cancelarAgendamentoPorId(anyLong());

        mockMvc
                .perform(put("/agendamento/{id}", anyLong()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(agendamentoService, times(1)).cancelarAgendamentoPorId(anyLong());
    }
}
