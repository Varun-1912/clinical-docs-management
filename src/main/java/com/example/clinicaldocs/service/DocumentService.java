package com.example.clinicaldocs.service;

import com.example.clinicaldocs.model.*;
import com.example.clinicaldocs.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final DocumentRepository documentRepo;
    private final VersionRepository versionRepo;
    private final AuditLogRepository auditRepo;

    public DocumentService(DocumentRepository documentRepo, VersionRepository versionRepo, AuditLogRepository auditRepo) {
        this.documentRepo = documentRepo;
        this.versionRepo = versionRepo;
        this.auditRepo = auditRepo;
    }

    public Document uploadDocument(String title, String trialId, MultipartFile file, String username) throws IOException {
        Document doc = new Document();
        doc.setTitle(title);
        doc.setTrialId(trialId);
        doc.setStatus("Pending");
        Document saved = documentRepo.save(doc);

        saveVersion(saved, file, username);
        logAction("UPLOAD", username, saved.getId(), "Uploaded initial version");
        return saved;
    }

    public Version saveVersion(Document doc, MultipartFile file, String username) throws IOException {
        List<Version> versions = versionRepo.findByDocumentIdOrderByVersionNoDesc(doc.getId());
        int nextVersion = versions.isEmpty() ? 1 : versions.get(0).getVersionNo() + 1;

        // ensure upload directory exists
        File baseDir = new File(uploadDir);
        if (!baseDir.exists()) baseDir.mkdirs();

        String safeFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String path = uploadDir + File.separator + "doc_" + doc.getId() + "_v" + nextVersion + "_" + safeFileName;
        File dest = new File(path);
        file.transferTo(dest);

        Version v = new Version();
        v.setDocument(doc);
        v.setVersionNo(nextVersion);
        v.setFilePath(path);
        v.setFileName(file.getOriginalFilename());
        Version saved = versionRepo.save(v);

        // attach to document for immediate read (optional)
        doc.addVersion(saved);
        documentRepo.save(doc);

        logAction("NEW_VERSION", username, doc.getId(), "Added version " + nextVersion);
        return saved;
    }

    public List<Document> getAllDocuments() { return documentRepo.findAll(); }

    public Optional<Document> getDocument(Long id) { return documentRepo.findById(id); }

    public Optional<Version> getVersion(Long docId, Integer versionNo) {
        List<Version> list = versionRepo.findByDocumentIdOrderByVersionNoDesc(docId);
        if (versionNo == null) return list.stream().findFirst();
        return list.stream().filter(v -> v.getVersionNo() == versionNo).findFirst();
    }

    private void logAction(String action, String username, Long docId, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setUsername(username);
        log.setDocumentId(docId);
        log.setDetails(details);
        auditRepo.save(log);
    }
}
