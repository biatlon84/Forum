package com.biathlon84.forum.section;

import com.biathlon84.forum.model.entity.Section;
import com.biathlon84.forum.model.front.SectionFront;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    public Section findOne(int id) {
        return sectionRepository.findById(id).get();
    }

    public Section findByName(String name) {
        return sectionRepository.findByName(name);
    }

    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    public void delete(int id) {
        delete(findOne(id));
    }

    public void delete(Section section) {
        sectionRepository.delete(section);
    }

    public Page<Section> findSections(Pageable pageable) {
        return sectionRepository.findAll(pageable);
    }
    public Section getDefault(){
        return new Section(0,"Section default name","Section description");
    }
    public List<SectionFront> convertToFront(List<Section> sections){
        return sections.stream().map(a->SectionFront.builder().name(a.getName()).build()).collect(Collectors.toList());
    }
}
