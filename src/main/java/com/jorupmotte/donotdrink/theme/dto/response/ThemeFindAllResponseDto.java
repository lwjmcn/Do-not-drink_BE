package com.jorupmotte.donotdrink.theme.dto.response;

import com.jorupmotte.donotdrink.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.theme.model.Theme;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class ThemeFindAllResponseDto extends ResponseDto {
    private final List<ThemeDto> themes;

    private ThemeFindAllResponseDto(List<Theme> themeList){
        super();
        this.themes = themeList
                .stream()
                .map(ThemeDto::from)
                .toList();
    }

    public static ResponseEntity<ThemeFindAllResponseDto> success(List<Theme> themeList) {
        ThemeFindAllResponseDto responseBody = new ThemeFindAllResponseDto(themeList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // immutable class
    private record ThemeDto(Long themeId, String name, String description, String color, String fileUrl) {
        public static ThemeDto from(Theme theme) {
                return new ThemeDto(
                        theme.getId(),
                        theme.getName(),
                        theme.getDescription(),
                        theme.getColor(),
                        theme.getFileUrl()
                );
        }
    }
}
