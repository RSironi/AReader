package com.tcc.areader.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.areader.model.Group;
import com.tcc.areader.model.LibraryBook;

public interface GroupRepository extends JpaRepository<Group, Long>{

    Optional<Group> findGroupByLibraryBook(LibraryBook libraryBook);

    List<Group> findByOwner(String userEmail);

    @Query("SELECT g FROM Group g WHERE :member IN elements(g.members)")
    List<Group> findByMembersContain(@Param("member") String userEmail);
}