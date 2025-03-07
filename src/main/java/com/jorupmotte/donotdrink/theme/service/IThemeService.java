package com.jorupmotte.donotdrink.theme.service;

import com.jorupmotte.donotdrink.theme.dto.response.ThemeFindAllResponseDto;
import org.springframework.http.ResponseEntity;

public interface IThemeService {
    ResponseEntity<? super ThemeFindAllResponseDto> findAllThemes();
}
