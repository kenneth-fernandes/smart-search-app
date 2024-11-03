package com.smartsearchapp.service;

import com.smartsearchapp.util.FileConversionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class TranscriptionService {

    private final TranscribeClient transcribeClient;

    @Value("aws.transcribe.s3-bucket-name")
    private String s3BucketName;
    public TranscriptionService(TranscribeClient transcribeClient) {
        this.transcribeClient = transcribeClient;
    }

    public String transcribeAudio(MultipartFile file) throws IOException, InterruptedException {
        File audioFile = FileConversionUtil.convertMultipartFileToFile(file);

        // Start Transcription Job
        String jobName = "TranscriptionJob_" + UUID.randomUUID();
        StartTranscriptionJobRequest transcriptionJobRequest = StartTranscriptionJobRequest.builder()
                .languageCode(LanguageCode.EN_US)
                .media(Media.builder().mediaFileUri(s3BucketName + audioFile.getName()).build())
                .transcriptionJobName(jobName)
                .build();

        transcribeClient.startTranscriptionJob(transcriptionJobRequest);

        // Poll until the transcription is complete
        GetTranscriptionJobResponse transcriptionJob;
        do {
            Thread.sleep(1000);
            transcriptionJob = transcribeClient.getTranscriptionJob(GetTranscriptionJobRequest.builder()
                    .transcriptionJobName(jobName)
                    .build());
        } while (transcriptionJob.transcriptionJob().transcriptionJobStatus() == TranscriptionJobStatus.IN_PROGRESS);

        // Retrieve and return the transcript text
        String transcriptUrl = transcriptionJob.transcriptionJob().transcript().transcriptFileUri();
        FileConversionUtil.deleteFile(audioFile);  // Clean up temporary file

        return FileConversionUtil.fetchTranscriptionText(transcriptUrl);
    }
}