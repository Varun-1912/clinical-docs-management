package com.example.clinicaldocs.repository;

import com.example.clinicaldocs.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> { }
