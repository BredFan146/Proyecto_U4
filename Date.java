import java.util.Calendar;
public class Date {
    static Calendar Date = Calendar.getInstance();
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public String dateString(){
        return (this.day + "/" + this.month + "/" + this.year);
    }
    public void setBorrowedDate(){
        this.day = Date.get(Calendar.DATE);
        this.month = Date.get(Calendar.MONTH);
        this.year = Date.get(Calendar.YEAR);
    }
}
