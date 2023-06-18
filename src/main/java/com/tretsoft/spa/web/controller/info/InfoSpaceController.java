package com.tretsoft.spa.web.controller.info;

import com.tretsoft.spa.model.info.Space;
import com.tretsoft.spa.model.info.SpaceDto;
import com.tretsoft.spa.service.info.SpaceService;
import com.tretsoft.spa.web.controller.ExceptionHandlerController;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/info-space")
public class InfoSpaceController extends ExceptionHandlerController {

    private final SpaceService spaceService;


    @GetMapping
    public List<SpaceDto> getSpaces(@RequestParam(name = "isPrivate", required = false, defaultValue = "true") Boolean isPrivate) {

        log.info("Get spaces. scope=" + (isPrivate ? "private" : "public"));

        return spaceService
                .getAll(isPrivate)
                .stream()
                .map(Space::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public SpaceDto createSpace(@RequestBody SpaceDto dto) {
        return spaceService.create(dto).toDto();
    }

    @PutMapping
    public SpaceDto updateSpace(@RequestBody SpaceDto dto) {
        log.info("Update space: " + dto);
        return spaceService.update(dto).toDto();
    }

    @DeleteMapping
    public void deleteSpace(@RequestBody SpaceDto dto) {
        spaceService.delete(dto.id());
    }
}
