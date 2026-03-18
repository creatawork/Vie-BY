package com.vie.service.service;

import com.vie.service.dto.RagDocumentAddRequest;
import com.vie.service.dto.RagRequest;
import com.vie.service.dto.RagResponse;

public interface RagService {
    RagResponse query(RagRequest request);

    void addDocument(RagDocumentAddRequest request);
}

