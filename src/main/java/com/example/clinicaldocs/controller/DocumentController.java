package com.example.clinicaldocs.controller;

import com.example.clinicaldocs.model.*;
import com.example.clinicaldocs.service.DocumentService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) { this.documentService = documentService; }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam String title,
                                    @RequestParam(required = false) String trialId,
                                    @RequestParam MultipartFile file,
                                    @RequestParam(defaultValue = "system") String username) {
        try {
            Document d = documentService.uploadDocument(title, trialId, file, username);
            return ResponseEntity.ok(d);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/version")
    public ResponseEntity<?> addVersion(@PathVariable Long id,
                                        @RequestParam MultipartFile file,
                                        @RequestParam(defaultValue = "system") String username) {
        try {
            Document doc = documentService.getDocument(id).orElse(null);
            if (doc == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
            Version v = documentService.saveVersion(doc, file, username);
            return ResponseEntity.ok(v);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Document> list() { return documentService.getAllDocuments(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return documentService.getDocument(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found"));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> download(@PathVariable Long id, @RequestParam(required = false) Integer version) {
        try {
            var versionOpt = documentService.getVersion(id, version);
            if (versionOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Version not found");
            Version v = versionOpt.get();
            File f = new File(v.getFilePath());
            if (!f.exists()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File missing on disk");
            FileSystemResource resource = new FileSystemResource(f);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(v.getFileName()).build());
            headers.setContentLength(f.length());
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
