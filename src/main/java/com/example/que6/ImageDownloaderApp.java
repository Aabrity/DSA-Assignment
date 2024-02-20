package com.example.que6;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageDownloaderApp extends JFrame {
    private final ExecutorService executorService;
    private final JTextField urlTextField;
    private final JButton downloadButton;
    private final JButton pauseButton; 
    private final JButton resumeButton; 
    private final JButton cancelButton;
    private final JPanel imagePanel;
    private final List<DownloadTask> downloadTasks;
    private final Lock lock;



    public ImageDownloaderApp() {
        setTitle("Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        executorService = Executors.newFixedThreadPool(5); 

        urlTextField = new JTextField();
        downloadButton = new JButton("Download");
        pauseButton = new JButton("Pause"); 
        resumeButton = new JButton("Resume"); 
        cancelButton = new JButton("Cancel all");
        imagePanel = new JPanel(new GridLayout(0, 1));
        downloadTasks = new ArrayList<>();
        lock = new ReentrantLock(); // Creating a ReentrantLock for synchronization

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField.getText().trim();
                if (!url.isEmpty()) {
                    startImageDownload(url);
                } else {
                    JOptionPane.showMessageDialog(ImageDownloaderApp.this, "Please enter a valid URL");
                }
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownloads();
            }
        });

        // Action listener for resume button
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownloads();
            }
        });

        // Action listener for cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownloads();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(downloadButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(cancelButton);

        // Panel for input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Enter URL: "), BorderLayout.WEST);
        inputPanel.add(urlTextField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        // Adding components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(imagePanel), BorderLayout.CENTER);


        setSize(800, 600);
        setVisible(true);
    }

    private void startImageDownload(String url) {
        DownloadTask downloadTask = new DownloadTask(url);
        lock.lock(); // Acquiring the lock before modifying shared resources
        try {
            downloadTasks.add(downloadTask);
            executorService.submit(downloadTask);
        } finally {
            lock.unlock(); // Releasing the lock after modifying shared resources
        }
    }
    private void pauseDownloads() {
        for (DownloadTask task : downloadTasks) {
            task.pauseDownload();

        }
    }

    // Method to resume downloads
    private void resumeDownloads() {
        for (DownloadTask task : downloadTasks) {
            task.resumeDownload();
        }
    }

    // Method to cancel downloads
    private void cancelDownloads() {
        for (DownloadTask task : downloadTasks) {
            task.cancelDownload();
        }
        downloadTasks.clear();
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void updateProgress(int index, int progress) {
        if (index >= 0 && index < downloadTasks.size()) {
            DownloadTask task = downloadTasks.get(index);
            task.setProgress(progress);
        }
    }

    private void displayImage(BufferedImage image) {
        lock.lock(); // Acquiring the lock before modifying shared resources
        try {
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imagePanel.add(imageLabel);
            imagePanel.revalidate();
            imagePanel.repaint();
        } finally {
            lock.unlock(); // Releasing the lock after modifying shared resources
        }
    }

    class DownloadTask implements Runnable {
        private final String url;
        private int progress;
        private final JProgressBar progressBar;
        private final JLabel statusLabel;
        private final JLabel percentageLabel;
        private volatile boolean isPaused = false;
        private volatile boolean isCancelled = false;

        public void pauseDownload() {
            isPaused = true;
            statusLabel.setText("Paused.");

        }

        public void resumeDownload() {
            isPaused = false;
            statusLabel.setText("Downloading...");
        }

        public void cancelDownload() {
            isCancelled = true;
            statusLabel.setText("cancelled.");
        }

        public DownloadTask(String url) {
            this.url = url;
            this.progress = 0;
            int maxLines = 10;
            this.progressBar = new JProgressBar(0, maxLines);
            this.statusLabel = new JLabel("Downloading...");
            this.percentageLabel = new JLabel("0%");


            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

            taskPanel.add(statusLabel);
            taskPanel.add(progressBar);
            taskPanel.add(percentageLabel);

            imagePanel.add(taskPanel);

            progressBar.setStringPainted(true);


        }

        public void setProgress(int progress) {
            this.progress = progress;
            SwingUtilities.invokeLater(() -> {
                if (progress == -1) {
                    progressBar.setString("Failed");
                    statusLabel.setText("Failed");
                } else if (progress == 100) {
                    progressBar.setValue(100);
                    progressBar.setString("Downloaded");
                    percentageLabel.setText("100%");
                    statusLabel.setText("Downloaded");
                } else {
                    progressBar.setValue(progress);
                    progressBar.setString(progress + "%");
                    percentageLabel.setText(progress + "%");
                    statusLabel.setText("Downloading...");
                }
            });
        }
        private String generateFileName() {
            // Generate a unique file name based on the current timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            return "image_" + timestamp + ".jpg";
        }


        @Override

        public void run() {
            try {
                URL imageUrl = new URL(url);
                BufferedImage image = ImageIO.read(imageUrl);

                for (int i = 0; i <= 100; i++) {
                    setProgress(i); // Update progress
                    try {
                        // Slow down the download process by sleeping the thread
                        Thread.sleep(50); // Adjust the delay time as needed (50 milliseconds in this example)
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ImageDownloaderApp.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // Check if the download process should be paused
                    while (isPaused) {
                        try {
                            Thread.sleep(100); // Wait for 100 milliseconds before checking again
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ImageDownloaderApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // Check if the download process should be cancelled
                    if (isCancelled) {
                        return; // Exit the download task if cancelled
                    }
                }

                if (image != null) {
                    String desktopPath = System.getProperty("user.home") + "/Desktop/";
                    String fileName = generateFileName();
                    File outputFile = new File(desktopPath + fileName);
                    ImageIO.write(image, "jpg", outputFile);
                    setProgress(100); // Set progress to 100% when the image is successfully downloaded
                } else {
                    throw new IOException("Failed to download image from: " + url);
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(ImageDownloaderApp.this, "Error downloading image" + "\n" + "Please check the validity of the URL" + "\n" + e.getMessage(), "Download Error", JOptionPane.ERROR_MESSAGE);
                });
                setProgress(-1); // Set progress to -1 on failure
            } finally {
                // Clean up resources
                if (!isCancelled) {
                    SwingUtilities.invokeLater(() -> {
                        imagePanel.remove(progressBar.getParent());
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    });
                }
            }}

        public int getProgress() {
            return progress;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageDownloaderApp();
        });
    }
}


