package com.jorupmotte.donotdrink.theme.service;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.theme.dto.response.ThemeFindAllResponseDto;
import com.jorupmotte.donotdrink.theme.model.Theme;
import com.jorupmotte.donotdrink.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService implements IThemeService {
    private final ThemeRepository themeRepository;

    @Override
    public ResponseEntity<? super ThemeFindAllResponseDto> findAllThemes() {
        List<Theme> themeList= themeRepository.findAll();
        if(themeList.isEmpty())
            return ResponseDto.databaseError();

        return ThemeFindAllResponseDto.success(themeList);
    }
}
