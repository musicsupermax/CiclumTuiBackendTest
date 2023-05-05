package com.tui.backend.mapper;

import com.tui.backend.dto.RepositoryInfo;
import org.eclipse.egit.github.core.Repository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

//todo: think about replacing converters with MapStruct
@Component
public class UserInfoConverter implements Converter<RepositoryInfo, Repository> {

    @Override
    public RepositoryInfo toDto(Repository entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        RepositoryInfo repositoryInfo = new RepositoryInfo();
        repositoryInfo.setName(entity.getName());
        Optional.ofNullable(entity.getOwner()).ifPresent(user -> repositoryInfo.setUserLogin(user.getLogin()));
        return repositoryInfo;
    }
}