package com.example.apiproject.pharmacy.service;

import com.example.apiproject.pharmacy.entity.Pharmacy;
import com.example.apiproject.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if (pharmacy == null) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }
}
