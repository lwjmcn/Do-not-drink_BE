package com.jorupmotte.donotdrink.expense.dto.response;

import com.jorupmotte.donotdrink.common.dto.response.ResponseDto;
import com.jorupmotte.donotdrink.expense.model.ExpenseCategory;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class CategoryListResponseDto extends ResponseDto {
    private final List<CategoryDto> categories;

    public CategoryListResponseDto(List<CategoryDto> categories) {
        super();
        this.categories = categories;
    }

    public static ResponseEntity<CategoryListResponseDto> success(List<CategoryDto> categories) {
        CategoryListResponseDto responseBody = new CategoryListResponseDto(categories);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public record CategoryDto(Long categoryId, String name, int amount){
        public static CategoryDto from(ExpenseCategory category, Long amount) {
            return new CategoryDto(
                    category.getId(),
                    category.getName(),
                    amount.intValue()
            );
        }

        public int compareTo(CategoryDto categoryDto) {
            return this.amount - categoryDto.amount;
        }
    }
}
