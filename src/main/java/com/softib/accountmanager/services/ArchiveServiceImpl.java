package com.softib.accountmanager.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.softib.accountmanager.entities.Account;
import com.softib.accountmanager.entities.Archive;
import com.softib.accountmanager.repositories.ArchiveRepository;

public class ArchiveServiceImpl {
	@Autowired
	private ArchiveRepository archiveRepository;

	public Collection<Archive> findAllArchive() {
		return archiveRepository.findAll();
	}

	public Collection<Archive> findArchiveByType(String type) {
		return archiveRepository.findByType(type);

	}

}
