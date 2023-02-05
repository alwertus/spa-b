package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.model.info.Space;
import com.tretsoft.spa.model.info.SpaceDto;
import com.tretsoft.spa.service.info.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/info")
public class InfoController extends BaseController {

    private final SpaceService spaceService;


    @GetMapping("/space")
    public List<SpaceDto> getSpaces(@RequestParam(name = "isPrivate", required = false, defaultValue = "true") Boolean isPrivate) {

        log.info("Get spaces. scope=" + (isPrivate ? "private" : "public"));

        return spaceService
                .getAll(isPrivate)
                .stream()
                .map(Space::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/space")
    public SpaceDto createSpace(@RequestBody SpaceDto dto) {
        return spaceService.create(dto).toDto();
    }

    @PutMapping("/space")
    public SpaceDto updateSpace(@RequestBody SpaceDto dto) {
        return spaceService.update(dto).toDto();
    }

    @DeleteMapping("/space")
    public void deleteSpace(@RequestBody SpaceDto dto) {
        spaceService.delete(dto.id());
    }

}
