package com.mottu.motolocation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.motolocation.dto.MotoDTO;
import com.mottu.motolocation.service.MotoService;
import com.mottu.motolocation.service.MovimentacaoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final MotoService motoService;
    private final MovimentacaoService movimentacaoService;
    private final ObjectMapper objectMapper;

    public ExportController(MotoService motoService, MovimentacaoService movimentacaoService, ObjectMapper objectMapper) {
        this.motoService = motoService;
        this.movimentacaoService = movimentacaoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/all-data")
    public String exportAllData() throws JsonProcessingException {
        Map<String, Object> allData = new HashMap<>();

        Page<MotoDTO> motos = motoService.listMotos(0, Integer.MAX_VALUE, "id", "");
        allData.put("motos", motos.getContent());

        Map<Long, Page<com.mottu.motolocation.dto.MovimentacaoDTO>> movimentacoesByMoto = motos.getContent().stream()
                .collect(Collectors.toMap(MotoDTO::getId,
                        moto -> movimentacaoService.listarMovimentacoesPorMoto(moto.getId(), 0, Integer.MAX_VALUE, "id")));
        allData.put("movimentacoes", movimentacoesByMoto);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allData);
    }
}
