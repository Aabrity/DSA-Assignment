
//The code implements a social media application using
// Java Swing, providing users with a platform to create accounts, share content,
// interact with other users' content, and build networks by following other users.
//  It utilizes a graph data structure to represent the social network,
// where nodes represent users and edges represent connections or interactions between users.
//  User interactions such as sharing content and liking content are tracked to build user profiles
//   and generate personalized content recommendations. Recommendations are based on the user's profile,
//  interactions, and network connections, and are displayed in a pop-up dialog. Additionally, users can
//  provide feedback on recommended content by liking it. The application also features content filtering,
//  allowing users to search for specific users and view their shared content, as well as a user search
//  functionality to find other users by their usernames. Real-time content updates in the news
//   feed display the latest shared content from users they follow. Overall, the application provides a
//  user-friendly interface with comprehensive functionalities for social interaction and content discovery
package com.example.que7Social;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SocialNetwork extends JFrame {
    private JPanel mainPanel;
    private JTextArea contentTextArea;
    private JButton shareButton;
    private JButton newUserButton;
    private JButton contentButton;
    private JButton newsFeedButton;
    private JButton followButton;
    private JButton unfollowButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton likeButton;
    private JButton recommendationButton;
    private JButton searchButton;

    private JTextField usernameField;
    private JButton loginButton;

    private Map<String, ArrayList<String>> userContentMap;
    private Map<String, Set<String>> userFollowersMap;
    private Map<String, Set<String>> userFollowingMap;
    private Map<String, ArrayList<String>> similarContentMap;
    private Map<String, Integer> contentLikesMap;

    private Graph socialGraph;

    private String currentUser;
    private ArrayList<String> newsFeedContent;
    private int currentContentIndex;

    public SocialNetwork() {
        setTitle("Social Network");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        userContentMap = new HashMap<>();
        userFollowersMap = new HashMap<>();
        userFollowingMap = new HashMap<>();
        similarContentMap = new HashMap<>();
        socialGraph = new Graph();
        contentLikesMap = new HashMap<>();

        contentTextArea = new JTextArea(5, 20);
        shareButton = new JButton("Share");
        newUserButton = new JButton("New User");
        contentButton = new JButton("My Content");
        newsFeedButton = new JButton("News Feed");
        followButton = new JButton("Follow");
        unfollowButton = new JButton("Unfollow");
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        likeButton = new JButton("Like");
        recommendationButton = new JButton("Recommendations");
        searchButton = new JButton("Search");

        JPanel loginPanel = new JPanel(new FlowLayout());
        usernameField = new JTextField(15);
        loginButton = new JButton("Login");
        loginPanel.add(new JLabel("Username: "));
        loginPanel.add(usernameField);
        loginPanel.add(loginButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new JLabel("Content:"), BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentTextArea), BorderLayout.CENTER);
        contentPanel.add(shareButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));

        buttonPanel.add(contentButton);
        buttonPanel.add(newsFeedButton);
        buttonPanel.add(followButton);
        buttonPanel.add(unfollowButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(likeButton);
        buttonPanel.add(recommendationButton);
        buttonPanel.add(searchButton);

        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(newUserButton, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);

        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = contentTextArea.getText();
                if (currentUser != null && userContentMap.containsKey(currentUser)) {
                    userContentMap.get(currentUser).add(content);
                    updateProfile(currentUser, content);
                    shareContentWithFollowers(currentUser, content);
                    JOptionPane.showMessageDialog(null, "Content shared successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first!");
                }
            }
        });

        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter new username:");
                if (username != null && !userContentMap.containsKey(username)) {
                    addUser(username);
                    JOptionPane.showMessageDialog(null, "New user created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or user already exists!");
                }
            }
        });

        contentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null && userContentMap.containsKey(currentUser)) {
                    displayUserContent(currentUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first!");
                }
            }
        });

        newsFeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null && userFollowingMap.containsKey(currentUser)) {
                    displayNewsFeed();
                } else {
                    JOptionPane.showMessageDialog(null, "Please login and follow users first!");
                }
            }
        });

        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userToFollow = JOptionPane.showInputDialog("Enter the username to follow:");
                if (userToFollow != null && userContentMap.containsKey(userToFollow)) {
                    followUser(currentUser, userToFollow);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username!");
                }
            }
        });

        unfollowButton.addActionListener(new ActionListener() { // ActionListener for unfollow button
            @Override
            public void actionPerformed(ActionEvent e) {
                String userToUnfollow = JOptionPane.showInputDialog("Enter the username to unfollow:");
                if (userToUnfollow != null && userFollowingMap.containsKey(currentUser)) {
                    if (userFollowingMap.get(currentUser).contains(userToUnfollow)) {
                        userFollowingMap.get(currentUser).remove(userToUnfollow);
                        userFollowersMap.get(userToUnfollow).remove(currentUser);
                        JOptionPane.showMessageDialog(null, "You unfollowed " + userToUnfollow);
                    } else {
                        JOptionPane.showMessageDialog(null, "You are not following " + userToUnfollow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or you are not following the user!");
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newsFeedContent != null && currentContentIndex < newsFeedContent.size() - 1) {
                    currentContentIndex++;
                    contentTextArea.setText(newsFeedContent.get(currentContentIndex));
                } else {
                    JOptionPane.showMessageDialog(null, "No more content to display!");
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newsFeedContent != null && currentContentIndex > 0) {
                    currentContentIndex--;
                    contentTextArea.setText(newsFeedContent.get(currentContentIndex));
                } else {
                    JOptionPane.showMessageDialog(null, "No previous content to display!");
                }
            }
        });

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null && newsFeedContent != null && currentContentIndex >= 0 && currentContentIndex < newsFeedContent.size()) {
                    String likedContent = newsFeedContent.get(currentContentIndex);
                    if (!contentLikesMap.containsKey(likedContent)) {
                        contentLikesMap.put(likedContent, 0);
                    }
                    contentLikesMap.put(likedContent, contentLikesMap.get(likedContent) + 1);
                    JOptionPane.showMessageDialog(null, "You liked the content!");
                } else {
                    JOptionPane.showMessageDialog(null, "Please login and view news feed first!");
                }
            }
        });

        recommendationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    displayNewsFeedRecommendations();
                } else {
                    JOptionPane.showMessageDialog(null, "Please login first!");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() { // ActionListener for search button
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchUsername = JOptionPane.showInputDialog("Enter the username to search:");
                if (searchUsername != null && userContentMap.containsKey(searchUsername)) {
                    displayUserContent(searchUsername);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found!");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (username != null && userContentMap.containsKey(username)) {
                    currentUser = username;
                    JOptionPane.showMessageDialog(null, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username!");
                }
            }
        });
    }

    private void addUser(String username) {
        userContentMap.put(username, new ArrayList<>());
        userFollowersMap.put(username, new HashSet<>());
        userFollowingMap.put(username, new HashSet<>());
        socialGraph.addNode(username);
    }

    private void shareContentWithFollowers(String currentUser, String content) {
        Set<String> followers = userFollowersMap.get(currentUser);
        for (String follower : followers) {
            userContentMap.get(follower).add(content);
        }
    }

    private void updateProfile(String currentUser, String content) {
        String[] words = content.split("\\s+");
        if (words.length > 0) {
            String firstWord = words[0];
            if (!similarContentMap.containsKey(firstWord)) {
                similarContentMap.put(firstWord, new ArrayList<>());
            }
            similarContentMap.get(firstWord).add(content);
        }
    }

    private void displayUserContent(String username) {
        JTextArea userContentArea = new JTextArea(10, 20);
        userContentArea.setEditable(false);
        if (userContentMap.containsKey(username)) {
            for (String content : userContentMap.get(username)) {
                userContentArea.append(content + "\n");
            }
        }
        JOptionPane.showMessageDialog(null, new JScrollPane(userContentArea), username + "'s Content", JOptionPane.PLAIN_MESSAGE);
    }

    private void displayNewsFeed() {
        newsFeedContent = new ArrayList<>();
        Set<String> following = userFollowingMap.get(currentUser);
        for (String user : following) {
            if (userContentMap.containsKey(user)) {
                for (String content : userContentMap.get(user)) {
                    if (!contentLikesMap.containsKey(content)) {
                        newsFeedContent.add(user + ": " + content);
                    }
                }
            }
        }
        if (!newsFeedContent.isEmpty()) {
            currentContentIndex = 0;
            contentTextArea.setText(newsFeedContent.get(currentContentIndex));
        } else {
            JOptionPane.showMessageDialog(null, "Your news feed is empty!");
        }
    }

    private void followUser(String currentUser, String userToFollow) {
        userFollowingMap.get(currentUser).add(userToFollow);
        userFollowersMap.get(userToFollow).add(currentUser);
        JOptionPane.showMessageDialog(null, "You are now following " + userToFollow);
    }

    private void displayNewsFeedRecommendations() {
        StringBuilder recommendations = new StringBuilder();
        Set<String> following = userFollowingMap.get(currentUser);
        for (String user : following) {
            if (userContentMap.containsKey(user)) {
                for (String content : userContentMap.get(user)) {
                    if (!contentLikesMap.containsKey(content)) {
                        recommendations.append(user + ": " + content + "\n");
                    }
                }
            }
        }
        if (recommendations.length() > 0) {
            JOptionPane.showMessageDialog(null, recommendations.toString(), "Recommendations", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No recommendations available!");
        }
    }

    private class Graph {
        private Map<String, Set<String>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        public void addNode(String node) {
            adjacencyList.put(node, new HashSet<>());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SocialNetwork();
            }
        });
    }
}
