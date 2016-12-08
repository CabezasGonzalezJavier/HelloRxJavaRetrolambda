package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

/**
 * Created by javierg on 08/12/2016.
 */

public class GithubUserList {

    private String login;
    private String blog;
    private int public_repos;

    public String getLogin() {
        return login;
    }

    public String getBlog() {
        return blog;
    }

    public int getPublicRepos() {
        return public_repos;
    }

    public GithubUserList(String login, String blog, int public_repos) {
        this.login = login;
        this.blog = blog;
        this.public_repos = public_repos;
    }
}
