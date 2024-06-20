package org.spring.postservice.services;

import org.spring.postservice.clients.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class UploadService {
	private final UploadClient uploadClient;
	private final Executor virtualThreadExecutor;

	@Autowired
	public UploadService(UploadClient uploadClient, @Qualifier("taskExecutor") Executor virtualThreadExecutor) {
		this.uploadClient = uploadClient;
		this.virtualThreadExecutor = virtualThreadExecutor;
	}

	@Async("taskExecutor")
	public CompletableFuture<List<String>> uploadFile(List<MultipartFile> files) {
		List<CompletableFuture<String>> uploadFutures = files.stream()
				.map(file -> CompletableFuture.supplyAsync(() -> uploadClient.uploadFile(file), virtualThreadExecutor))
				.toList();

		return CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0]))
				.thenApply(ignored -> uploadFutures.stream()
						.map(CompletableFuture::join)
						.toList());
	}
}