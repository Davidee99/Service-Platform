package com.service;

import com.model.dto.SendAttachmentDTO;
import com.model.entity.Attachment;
import com.model.wrapper.ResponseWrapper;

public interface AttachmentService {

	ResponseWrapper<Attachment> sendAttachment(SendAttachmentDTO attachment);
}
