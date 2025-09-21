package com.mottu.motolocation.controller;

import com.mottu.motolocation.dto.MotoDTO;
import com.mottu.motolocation.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/motos")
public class MotoWebController {

    private final MotoService motoService;

    public MotoWebController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public String listarMotos(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<MotoDTO> motosPage = motoService.listMotos(page, size, "placa", null);
        model.addAttribute("motosPage", motosPage);
        return "motos/list";
    }

    // --- MÉTODOS NOVOS ADICIONADOS ABAIXO ---

    // Exibe o formulário de criação de nova moto
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("motoDTO", new MotoDTO());
        return "motos/form"; // Usaremos um arquivo form.html para criar e editar
    }

    // Processa o envio do formulário de criação
    @PostMapping
    public String createMoto(@Valid @ModelAttribute("motoDTO") MotoDTO motoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "motos/form"; // Se houver erros, volta para o formulário
        }
        motoService.createMoto(motoDTO);
        return "redirect:/web/motos"; // Redireciona para a lista após o sucesso
    }

    // Exibe o formulário de edição com os dados da moto
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MotoDTO motoDTO = motoService.getMotoById(id);
        model.addAttribute("motoDTO", motoDTO);
        return "motos/form";
    }

    // Processa o envio do formulário de edição
    @PostMapping("/{id}")
    public String updateMoto(@PathVariable Long id, @Valid @ModelAttribute("motoDTO") MotoDTO motoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "motos/form"; // Se houver erros, volta para o formulário
        }
        motoService.updateMoto(id, motoDTO);
        return "redirect:/web/motos";
    }

    // Processa a exclusão da moto
    @GetMapping("/delete/{id}")
    public String deleteMoto(@PathVariable Long id) {
        motoService.deleteMoto(id);
        return "redirect:/web/motos";
    }
}