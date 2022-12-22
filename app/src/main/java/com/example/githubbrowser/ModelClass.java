package com.example.githubbrowser;

public class ModelClass {
    String repo_name, description, html_url, docids;
    String issues_cnt;

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getIssues_cnt() {
        return issues_cnt;
    }

    public void setIssues_cnt(String issues_cnt) {
        this.issues_cnt = issues_cnt;
    }

    public String getDocids() {
        return docids;
    }


    public void setDocids(String docids) {
        this.docids = docids;
    }
}
