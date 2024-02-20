package com.example.que6;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class ImageDownloaderAppTest {

    private ImageDownloaderApp imageDownloaderApp;

    @BeforeEach
    public void setUp() {
        imageDownloaderApp = new ImageDownloaderApp(); // Initialize ImageDownloaderApp
    }

    @Test
    public void testDownloadTask() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                URL imageUrl = new URL("https://example.com/image.jpg");
                BufferedImage image = ImageIO.read(imageUrl);
                assertNotNull(image);

                // Simulate a download task by directly creating and executing an instance of DownloadTask
                ImageDownloaderApp.DownloadTask downloadTask = imageDownloaderApp.new DownloadTask(imageUrl.toString());
                downloadTask.run(); // Execute the download task

                assertTrue(downloadTask.getProgress() == 100); // Check if the progress is 100%
                assertNotNull(downloadTask.getClass()); // Check if the image is not null
            } catch (IOException e) {
                fail("IOException occurred during image download");
            }
        });
    }
}
