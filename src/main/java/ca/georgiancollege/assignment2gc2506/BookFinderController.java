package ca.georgiancollege.assignment2gc2506;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BookFinderController {

    @FXML
    private TextField bookSearchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> bookListView;

    @FXML
    private VBox detailsPane;

    @FXML
    private ImageView coverImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label descriptionLabel;

    private static final String API_KEY = "";

    @FXML
    void onSearch() {
        String query = bookSearchTextField.getText().trim();
        if (!query.isEmpty()) {
            fetchBooks(query);
        }
    }

    private void fetchBooks(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = connection.getInputStream();
                Scanner scanner = new Scanner(responseStream);
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                bookListView.getItems().clear();
                jsonResponse.getAsJsonArray("items").forEach(item -> {
                    JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
                    String title = volumeInfo.get("title").getAsString();
                    bookListView.getItems().add(title);
                });
            } else {
                InputStream errorStream = connection.getErrorStream();
                Scanner errorScanner = new Scanner(errorStream);
                String errorResponse = errorScanner.useDelimiter("\\A").next();
                errorScanner.close();
                System.err.println("Error Response: " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onBookSelected(MouseEvent event) {
        String selectedBookTitle = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBookTitle != null) {
            fetchBookDetails(selectedBookTitle);
        }
    }

    private void fetchBookDetails(String title) {
        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + encodedTitle + "&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream responseStream = connection.getInputStream();
            Scanner scanner = new Scanner(responseStream);
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            JsonObject volumeInfo = jsonResponse.getAsJsonArray("items").get(0).getAsJsonObject().getAsJsonObject("volumeInfo");

            titleLabel.setText("Title: " + volumeInfo.get("title").getAsString());
            authorLabel.setText("Author(s): " + volumeInfo.getAsJsonArray("authors").get(0).getAsString());
            yearLabel.setText("Published Year: " + volumeInfo.get("publishedDate").getAsString());
            genreLabel.setText("Genre: " + volumeInfo.getAsJsonArray("categories").get(0).getAsString());
            descriptionLabel.setText("Description: " + volumeInfo.get("description").getAsString());

            String coverUrl = volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
            coverImageView.setImage(new Image(coverUrl));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
