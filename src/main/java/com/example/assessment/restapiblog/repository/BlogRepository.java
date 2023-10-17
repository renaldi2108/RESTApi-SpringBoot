package com.example.assessment.restapiblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assessment.restapiblog.data.model.Content;

public interface BlogRepository extends JpaRepository<Content, Long> {
    
}