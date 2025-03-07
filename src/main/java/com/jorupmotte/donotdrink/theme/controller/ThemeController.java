package com.jorupmotte.donotdrink.theme.controller;

import com.jorupmotte.donotdrink.theme.dto.response.ThemeFindAllResponseDto;
import com.jorupmotte.donotdrink.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping("/")
    public ResponseEntity<? super ThemeFindAllResponseDto> findAll(){
        return themeService.findAllThemes();
    }
}
