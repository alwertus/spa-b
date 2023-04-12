package com.tretsoft.spa.service.info;

import com.tretsoft.spa.exception.BadRequestException;
import com.tretsoft.spa.exception.ForbiddenException;
import com.tretsoft.spa.exception.NullAttributeException;
import com.tretsoft.spa.model.user.SpaUser;
import com.tretsoft.spa.model.info.Space;
import com.tretsoft.spa.model.info.SpaceDto;
import com.tretsoft.spa.repository.SpaceRepository;
import com.tretsoft.spa.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SpaceService {

    private final SpaceRepository spaceRepository;

    private final AuthenticationService authenticationService;

    public List<Space> getAll(boolean isPrivate) {
        SpaUser user = authenticationService.getCurrentUser();
        return isPrivate
                ? spaceRepository.findAllByIsPrivateIsTrueAndCreatedByEquals(user)
                : spaceRepository.findAllByIsPrivateIsFalseOrIsPrivateIsNull();
    }

    public Space getById(Long spaceId) {
        // TODO: check available spaceid to current user
        return spaceRepository.findById(spaceId).orElseThrow(() -> new BadRequestException("spaceId"));
    }

    public Space create(SpaceDto dto) {
        Space space = Space
                        .builder()
                        .title(dto.title())
                        .created(Calendar.getInstance())
                        .createdBy(authenticationService.getCurrentUser())
                        .isPrivate(dto.isPrivate() == null || dto.isPrivate())
                        .build();

        if (space.getCreated() == null)
            throw new NullAttributeException(List.of("Created").toArray());
        if (space.getTitle() == null || space.getTitle().equals(""))
            throw new NullAttributeException(List.of("title").toArray());

        return spaceRepository.save(space);
    }

    public Space update(SpaceDto dto) {
        Space space = spaceRepository
                .findById(dto.id())
                .orElseThrow(() -> new com.tretsoft.spa.exception.BadRequestException("Space id='" + dto.id() + "' not found"));
        if (dto.title() != null) space.setTitle(dto.title());
        if (dto.isPrivate() != null) space.setIsPrivate(dto.isPrivate());
        return spaceRepository.save(space);
    }

    public void delete(Long spaceId) {
        Space space = spaceRepository
                .findById(spaceId)
                .orElseThrow(() -> new com.tretsoft.spa.exception.BadRequestException("Space id='" + spaceId + "' not found"));

        // TODO: check if admin => allow delete
        if (!space.getIsPrivate()) {
            throw new ForbiddenException("delete public space");
        }

        spaceRepository.deleteById(spaceId);


    }
}
