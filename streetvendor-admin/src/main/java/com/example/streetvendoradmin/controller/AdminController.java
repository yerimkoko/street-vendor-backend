package com.example.streetvendoradmin.controller;

import com.example.streetvendoradmin.controller.dto.request.LoginRequest;
import com.example.streetvendoradmin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import store.streetvendor.controller.ApiResponse;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        adminService.login(request);
        return ApiResponse.OK;
    }

}
