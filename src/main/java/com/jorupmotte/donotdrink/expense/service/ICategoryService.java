package com.jorupmotte.donotdrink.expense.service;

import com.jorupmotte.donotdrink.expense.dto.response.CategoryListResponseDto;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    public Long getTransactionSumByUserCategory(Long userId, Long categoryId);
    public ResponseEntity<? super CategoryListResponseDto> getCategories();
}
