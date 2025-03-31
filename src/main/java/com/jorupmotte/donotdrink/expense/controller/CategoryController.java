package com.jorupmotte.donotdrink.expense.controller;

import com.jorupmotte.donotdrink.expense.dto.response.CategoryListResponseDto;
import com.jorupmotte.donotdrink.expense.dto.response.TransactionListInCategoryResponseDto;
import com.jorupmotte.donotdrink.expense.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/me/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<? super CategoryListResponseDto> getCategories(){
        return categoryService.getCategories();
    }
}
