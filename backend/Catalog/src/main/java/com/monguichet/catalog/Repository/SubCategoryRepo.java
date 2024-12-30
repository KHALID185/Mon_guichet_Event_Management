package com.monguichet.catalog.Repository;

import com.monguichet.catalog.Entity.Event;
import com.monguichet.catalog.Entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

     List<SubCategory> findByCategoryId(Long categoryId);

}