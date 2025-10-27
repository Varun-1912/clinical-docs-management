package com.example.clinicaldocs.repository;

import com.example.clinicaldocs.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
