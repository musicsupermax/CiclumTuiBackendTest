package com.tui.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryInfo {

    private String name;
    private String userLogin;
    private List<Branch> branches;

}