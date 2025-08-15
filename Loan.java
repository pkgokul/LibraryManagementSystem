import java.util.Date;

public class Loan {
    private int loanId;
    private int bookId;
    private int memberId;
    private Date issueDate;
    private Date returnDate;

    public Loan(int loanId, int bookId, int memberId, Date issueDate, Date returnDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }
}
