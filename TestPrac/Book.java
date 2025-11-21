package TestPrac;

public class Book {
    private String title;
    private String author;
    private int pages;

    public Book(String title, String author, int pages){
        this.title = title;
        this.author = author;
        if (pages >= 0) {
            this.pages = pages;
        } else {
            this.pages = 0; 
        }
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public int getPages(){
        return this.pages;
    }

    public void setPages(int amount){
        if (amount >= 0) {
            this.pages = amount;
        }
    }

    public boolean isLong(){
        return pages >= 400;
    }
}
