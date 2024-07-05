package com.example.DWTransferScoutProject.superadmin.controller;

import com.example.DWTransferScoutProject.superadmin.dto.SuperAdminDto;
import com.example.DWTransferScoutProject.superadmin.service.SuperAdminService;
import com.example.DWTransferScoutProject.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmins")
@AllArgsConstructor
public class SuperAdminController {
    private final SuperAdminService adminService;

    // Admin 업데이트 API
    @PutMapping("/{id}")
    public ResponseEntity<SuperAdminDto> updateAdmin(@PathVariable Long id, @RequestBody SuperAdminDto adminDto) {
        return ResponseEntity.ok(adminService.updateAccount(id, adminDto));
    }

    // Admin 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    // Admin 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<SuperAdminDto> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.mapToDTO(
                adminService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id))
        ));
    }
}
