package com.example.clinicaldocs.controller;

import com.example.clinicaldocs.model.AuditLog;
import com.example.clinicaldocs.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditLogRepository auditRepo;
    public AuditController(AuditLogRepository auditRepo) { this.auditRepo = auditRepo; }

    @GetMapping
    public List<AuditLog> list() { return auditRepo.findAll(); }
}
