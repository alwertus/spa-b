package com.tretsoft.spa.web.controller.util;

import com.tretsoft.spa.service.utility.IconService;
import com.tretsoft.spa.web.dto.content.IconDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/icon")
public class IconController {

    private final IconService iconService;

    @GetMapping
    public List<IconDto> getAll() {
        return iconService.getAll();
    }

}
