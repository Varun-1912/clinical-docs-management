package com.example.clinicaldocs.repository;

import com.example.clinicaldocs.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByDocumentIdOrderByVersionNoDesc(Long documentId);
}
