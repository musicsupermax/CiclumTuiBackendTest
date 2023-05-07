package com.tui.backend.container;

import com.tui.backend.constant.VcsRepositoryEnum;
import com.tui.backend.service.VcsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;

@Component
@RequiredArgsConstructor
public class VcsRepositoryStrategyContainer {

    private static final EnumMap<VcsRepositoryEnum, VcsRepository> container = new EnumMap<>(VcsRepositoryEnum.class);

    private final VcsRepository gitHubRepositoryService;

    @PostConstruct
    void init() {
        container.put(VcsRepositoryEnum.GITHUB, gitHubRepositoryService);
    }

    public VcsRepository getFromContainer(VcsRepositoryEnum vcsRepositoryEnum) {
        return container.getOrDefault(vcsRepositoryEnum, gitHubRepositoryService);
    }

}