package com.softib.accountmanager.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softib.accountmanager.entities.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Integer> {

	Collection<Archive> findByType(String type);

	Optional<Archive> findByContentId(Integer id);
}
