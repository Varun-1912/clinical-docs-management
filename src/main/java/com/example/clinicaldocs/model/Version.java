package com.example.clinicaldocs.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "versions")
public class Version {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int versionNo;
    private String filePath;
    private String fileName;
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getVersionNo() { return versionNo; }
    public void setVersionNo(int versionNo) { this.versionNo = versionNo; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public Document getDocument() { return document; }
    public void setDocument(Document document) { this.document = document; }
}
