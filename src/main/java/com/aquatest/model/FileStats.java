package com.aquatest.model;

import java.util.List;

public class FileStats {

    int client_id;
    int filesCount;
    List<FileInfo> filesInfo;

    public FileStats(int client_id, int filesCount, List<FileInfo> filesInfo) {
        this.client_id = client_id;
        this.filesCount = filesCount;
        this.filesInfo = filesInfo;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(int filesCount) {
        this.filesCount = filesCount;
    }

    public List<FileInfo> getFilesInfo() {
        return filesInfo;
    }

    public void setFilesInfo(List<FileInfo> filesInfo) {
        this.filesInfo = filesInfo;
    }

}
