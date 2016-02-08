package com.pink.triangle.hussain.file;

import io.searchbox.annotations.JestId;

import java.util.Date;

/**
 * Created by hmiah on 04-Feb-16.
 */
public class IngestFile {

    @JestId
    private String fileHash;
    private String documentContent;
    private long dateCreated;
    private long fileSize;
    private long dateIngested;
    private String filename;

    public IngestFile(String fileHash){
        this.fileHash = fileHash;
    }

    public String getFileHash() {
        return fileHash;
    }

    public Date getDateCreated() {
        return new Date(dateCreated);
    }

    public Date getDateIngested() {
        return new Date(dateIngested);
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public String getFilename(){
        return this.filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateIngested(long dateIngested) {
        this.dateIngested = dateIngested;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }
}
