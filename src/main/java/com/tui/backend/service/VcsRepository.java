package com.tui.backend.service;

import com.tui.backend.dto.RepositoryInfo;

import java.util.List;

public interface VcsRepository {

    List<RepositoryInfo> doFindAllBy(String username);

}