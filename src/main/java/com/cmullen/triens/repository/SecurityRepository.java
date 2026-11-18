package com.cmullen.triens.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmullen.triens.model.Security;

public interface SecurityRepository
        extends JpaRepository<Security, UUID> {
}