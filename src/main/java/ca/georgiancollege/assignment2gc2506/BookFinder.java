package ca.georgiancollege.assignment2gc2506;

public class BookFinder {
    private String title;
    private String[] author_name;
    private String cover_i;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return author_name != null && author_name.length > 0 ? author_name[0] : "Unknown";
    }

    public void setAuthorName(String[] author_name) {
        this.author_name = author_name;
    }

    public String getCoverUrl() {
        return cover_i != null ? "https://covers.openlibrary.org/b/id/" + cover_i + "-L.jpg" : "";
    }

    public void setCoverUrl(String cover_i) {
        this.cover_i = cover_i;
    }
}
