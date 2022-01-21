package com.biathlon84.forum.section;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biathlon84.forum.model.entity.Section;


public interface SectionRepository extends JpaRepository<Section, Integer> {
    
    Section findByName(String name);
    
}
