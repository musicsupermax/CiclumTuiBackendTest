package com.tui.backend.service;

import com.tui.backend.dto.Branch;
import com.tui.backend.dto.RepositoryInfo;
import com.tui.backend.facade.RepositoryServiceFacade;
import com.tui.backend.mapper.BranchConverter;
import com.tui.backend.mapper.RepositoryInfoConverter;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.TypedResource;
import org.eclipse.egit.github.core.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@ExtendWith(MockitoExtension.class)
public class GitHubRepositoryServiceTest {

    private static final String REPO_NAME = "RepoName";
    private static final String USER_LOGIN = "UserLogin";
    private static final String BRANCH_NAME = "BranchName";
    private static final String LAST_COMMIT_SHA = "LastCommitSha";
    private static final String USERNAME = "USERNAME";

    @Spy
    private BranchConverter branchConverter;

    @Spy
    private RepositoryInfoConverter repositoryInfoConverter;

    @Mock
    private RepositoryServiceFacade repositoryServiceFacade;

    @InjectMocks
    private GitHubRepositoryService gitHubRepositoryService;

    @Test
    void doFindAllBy_happyPath() {
        // build repository data
        Repository repository = buildRepository();
        RepositoryBranch repositoryBranch = buildBranch();

        Mockito.when(repositoryServiceFacade.getRepositories(USERNAME))
                .thenReturn((Collections.singletonList(repository)));
        Mockito.when(repositoryServiceFacade.getBranches(repository))
                .thenReturn(Collections.singletonList(repositoryBranch));

        List<RepositoryInfo> result = gitHubRepositoryService.doFindAllBy(USERNAME);

        assertThat(result, is(not(empty())));
        // assert repository info
        RepositoryInfo repositoryInfo = result.get(0);
        assertThat(repositoryInfo.getName(), is(repository.getName()));
        assertThat(repositoryInfo.getUserLogin(), is(repository.getOwner().getLogin()));
        // assert branch
        Branch branch = repositoryInfo.getBranches().get(0);
        assertThat(branch.getName(), is(repositoryBranch.getName()));
        assertThat(branch.getLastCommitSha(), is(repositoryBranch.getCommit().getSha()));
    }

    private Repository buildRepository() {
        User owner = new User();
        owner.setLogin(USER_LOGIN);

        Repository repository = new Repository();
        repository.setName(REPO_NAME);
        repository.setOwner(owner);

        return repository;
    }

    private RepositoryBranch buildBranch() {
        TypedResource lastCommit = new TypedResource();
        lastCommit.setSha(LAST_COMMIT_SHA);

        RepositoryBranch branch = new RepositoryBranch();
        branch.setName(BRANCH_NAME);
        branch.setCommit(lastCommit);

        return branch;
    }

}