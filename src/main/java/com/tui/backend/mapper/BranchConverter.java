package com.tui.backend.mapper;

import com.tui.backend.dto.Branch;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class BranchConverter implements Converter<Branch, RepositoryBranch> {

    @Override
    public Branch toDto(RepositoryBranch entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        Branch branch = new Branch();
        branch.setName(entity.getName());
        Optional.ofNullable(entity.getCommit()).ifPresent(commit -> branch.setLastCommitSha(commit.getSha()));
        return branch;
    }
}