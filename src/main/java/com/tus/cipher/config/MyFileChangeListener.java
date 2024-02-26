package com.tus.cipher.config;

import java.util.Set;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import com.tus.cipher.controllers.ImportController;

@Component
public class MyFileChangeListener implements FileChangeListener {

	private ImportController importController;

	MyFileChangeListener(ImportController importController){
		this.importController = importController;
	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for (ChangedFiles cfiles : changeSet) {
			for (ChangedFile cfile : cfiles.getFiles()) {

				 if (cfile.getType() == ChangedFile.Type.ADD) {
					 importController.autoImport(cfile.getFile().getName());
				 }
			}
		}
	}

}
