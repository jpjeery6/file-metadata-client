package com.aquatest.model;

public class FileInfo {

    String file;
    Long size;

    public FileInfo(String file, Long size) {
        this.file = file;
        this.size = size;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileInfo [file=" + file + ", size=" + size + "]";
    }

}
