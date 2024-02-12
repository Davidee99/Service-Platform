package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
