package com.example.DWTransferScoutProject.user.controller;

import com.example.DWTransferScoutProject.user.dto.GenderDto;
import com.example.DWTransferScoutProject.user.entity.GenderEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/genders")
public class GenderController {

    @GetMapping
    public ResponseEntity<List<GenderDto>> getGenders() {
        List<GenderDto> genders = Arrays.stream(GenderEnum.values())
                .map(gender -> new GenderDto(gender.name(), gender.getDisplayName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(genders);
    }
}
