package com.softib.accountmanager.services;

import java.util.Collection;

import com.softib.accountmanager.entities.Archive;

public interface ArchiveService {

	public void archive(Integer id);

	public void restore(Integer id);

	Collection<Archive> findAllArchive();

	Collection<Archive> findArchiveByType(String type);
}
