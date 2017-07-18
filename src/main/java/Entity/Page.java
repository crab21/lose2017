package Entity;

/**
 * Created by k on 17-7-13.
 */
public class Page {
    private  int pageCount = 0;
    private int page = 0;
    private final int pageSize = 10;

    public Page() {
    }

    public Page(int pageCount, int page) {
        this.pageCount = pageCount;
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
