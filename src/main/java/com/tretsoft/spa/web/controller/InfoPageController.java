package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.model.info.Page;
import com.tretsoft.spa.model.info.PageDto;
import com.tretsoft.spa.model.info.PageListItemDto;
import com.tretsoft.spa.model.info.Space;
import com.tretsoft.spa.service.info.PageService;
import com.tretsoft.spa.service.info.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/info-page")
public class InfoPageController extends BaseController {

    private final PageService pageService;
    private final SpaceService spaceService;

    @GetMapping("/list")
    public List<PageListItemDto> getPages(@RequestParam(name = "spaceId") Long spaceId) {
        log.info("Get pages by spaceId=" + spaceId);
        Space space = spaceService.getById(spaceId);
        List<Page> sourceList = pageService.getAll(space);
        List<PageListItemDto> response = extractChildrenElements(null, sourceList);
        return response;
    }

    private List<PageListItemDto> extractChildrenElements(Long parentId, List<Page> sourceList) {
        return sourceList.stream()
                .filter(e -> e.getParent() == null
                        ? parentId == null
                        : e.getParent().getId().equals(parentId))
                .map(e ->  new PageListItemDto(
                        e.getId(),
                        e.getTitle(),
                        e.getPosition(),
                        e.getSpace().getId(),
                        null,
                        extractChildrenElements(e.getId(), sourceList)
                    ))
                .collect(toList());
    }

    @GetMapping
    public PageDto getPage(@RequestParam(name = "spaceId") Long spaceId, @RequestParam(name = "pageId") Long pageId) {
        return pageService.getPage(spaceId, pageId).toPageDto();
    }

    @PostMapping
    public PageListItemDto createPage(@RequestBody PageListItemDto dto) {
        log.info("Create new page: " + dto);

        if (dto.spaceId() == null) {
            throw new BadRequestException("spaceId");
        }

        Page parentPage = null;
        if (dto.parentId() != null) {
            parentPage = pageService.getPage(dto.spaceId(), dto.parentId());
        }

        log.debug("Parent page: {}", parentPage);

        return pageService
                .create(Page
                        .builder()
                        .title(dto.title())
                        .position(dto.position())
                        .space(spaceService.getById(dto.spaceId()))
                        .parent(parentPage)
                        .build())
                .toPageListItemDto();
    }

    @DeleteMapping
    public void deletePage(@RequestBody PageListItemDto dto) {
        log.info("Delete page id=" + dto.id());
        pageService.delete(dto.spaceId(), dto.id());
    }

    @PutMapping("/list")
    public PageListItemDto updatePageList(@RequestBody PageListItemDto dto) {
        Page page = pageService.getPage(dto.spaceId(), dto.id());
        if (dto.position() != null) {
            page.setPosition(
                    (page.getPosition() == null
                            ? 0
                            : page.getPosition()
                    ) + dto.position());
        }
        if (dto.title() != null) page.setTitle(dto.title());
        if (dto.parentId() != null) page.setParent(pageService.getPage(dto.spaceId(), dto.parentId()));

        return pageService.update(page).toPageListItemDto();
    }

//    @PutMapping
//    updatePage
}
