package com.tretsoft.spa.service.info;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.model.info.Page;
import com.tretsoft.spa.model.info.Space;
import com.tretsoft.spa.repository.PageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public List<Page> getAll(Space space) {
        return pageRepository.findAllBySpace(space);
    }

    public List<Page> getAllChild(Page parent) {
        return pageRepository.findAllByParent(parent);
    }

    public Page getPage(Long spaceId, Long pageId) {
        Page page = pageRepository.getById(pageId);
        if (!page.getSpace().getId().equals(spaceId)) {
            throw new BadRequestException("Page id='" +  + pageId + "' not contains from space='" + spaceId + "'");
        }
        return page;
    }

    public Page create(Page page) {
        // TODO: check user can create into this space
        // TODO: set position
        return pageRepository.save(page);
    }

    public void delete(Long spaceId, Long pageId) {
        // TODO: check user can delete from this space
        if (pageId == null) {
            throw new BadRequestException("Page id is null");
        }
        Page parent = getPage(spaceId, pageId);

        if (getAllChild(parent).size() > 0) {
            throw new BadRequestException("Page contains child element. Delete them first");
        }

        pageRepository.deleteById(pageId);
    }

    public Page update(Page page) {
        // TODO: check user can update this page
        // TODO: if position updated -> switch position to another page
        return pageRepository.saveAndFlush(page);
    }

}
