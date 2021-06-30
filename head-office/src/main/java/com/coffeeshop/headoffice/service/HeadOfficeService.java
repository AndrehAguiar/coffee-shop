package com.coffeeshop.headoffice.service;

import com.coffeeshop.headoffice.controller.response.HeadOfficeResponse;
import com.coffeeshop.headoffice.model.dto.HeadOfficeDTO;
import com.coffeeshop.headoffice.model.entity.HeadOffice;
import com.coffeeshop.headoffice.repository.HeadOfficeRepository;
import com.coffeeshop.headoffice.resource.HeadOfficeResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HeadOfficeService {

    private final HeadOfficeRepository headOfficeRepository;
    private final HeadOfficeResource headOfficeResource;

    public HeadOfficeService(HeadOfficeRepository headOfficeRepository, HeadOfficeResource headOfficeResource) {
        this.headOfficeRepository = headOfficeRepository;
        this.headOfficeResource = headOfficeResource;
    }

    @Transactional
    public HeadOfficeResponse create(HeadOfficeDTO headOfficeDTO) {
        return headOfficeResource.createLinkDetail(new HeadOfficeDTO(headOfficeRepository.save(new HeadOffice(headOfficeDTO))));
    }

    @Transactional
    public HeadOfficeResponse update(HeadOfficeDTO headOfficeDTO) {
        return headOfficeResource.createLinkDetail(new HeadOfficeDTO(headOfficeRepository.save(new HeadOffice(headOfficeDTO))));
    }

    @Transactional
    public HeadOfficeDTO delete(Long id) {
        HeadOfficeDTO headOfficeDTO = new HeadOfficeDTO(headOfficeRepository.findById(id).orElseThrow());
        headOfficeRepository.delete(new HeadOffice(headOfficeDTO));
        return headOfficeDTO;
    }

    @Transactional(readOnly = true)
    public HeadOfficeResponse findById(Long id) {
        HeadOfficeDTO headOfficeDTO = new HeadOfficeDTO(headOfficeRepository.findById(id).orElseThrow());
        return headOfficeResource.createLinkDetail(headOfficeDTO);
    }

    @Transactional(readOnly = true)
    public Page<HeadOfficeResponse> findAll() {
        List<HeadOfficeDTO> headOfficeDTOList = StreamSupport.stream(headOfficeRepository
                .findAll().spliterator(), false).map(HeadOfficeDTO::new).collect(Collectors.toList());
        return new PageImpl<>(headOfficeDTOList.stream()
                .map(headOfficeResource::createLinkDetail).collect(Collectors.toList()));
    }

    @Transactional
    public HeadOfficeResponse activate(Long id) {
        HeadOffice headOffice = headOfficeRepository.findById(id).orElseThrow();
        headOffice.setIsInactive(false);
        return headOfficeResource.createLinkDetail(new HeadOfficeDTO(headOfficeRepository.save(headOffice)));
    }

    public HeadOfficeResponse inactivate(Long id) {
        HeadOffice headOffice = headOfficeRepository.findById(id).orElseThrow();
        headOffice.setIsInactive(true);
        return headOfficeResource.createLinkDetail(new HeadOfficeDTO(headOfficeRepository.save(headOffice)));
    }

    //TODO Add store to head office

    //TODO Get all stores from head office

    //TODO Get store from head office

    //TODO Get active stores from head office
}
