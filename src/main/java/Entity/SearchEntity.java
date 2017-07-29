package Entity;

/**
 * Created by k on 17-7-19.
 */
public class SearchEntity {
    private String searchInput;
    private String searchType;

    public SearchEntity() {
    }

    public SearchEntity(String searchInput, String searchType) {
        this.searchInput = searchInput;
        this.searchType = searchType;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}

