class Journal extends LibraryItem {
    private String eISSN;
    private String publisher;
    private String latestIssueDate;

    public Journal(String id, String title, String eISSN, String publisher, String issueDate) {
        super(id, title,3);
        this.eISSN = eISSN;
        this.publisher = publisher;
        this.latestIssueDate = issueDate;

        this.borrowPeriod = 3;
    }

    @Override
    public double getFineRate() {
        return 2.0;
    }

    public String getEISSN() {return eISSN;}
    public void setEISSN(String eISSN) {this.eISSN = eISSN;}

    public String getPublisher() {return publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}

    public String getLatestIssueDate() {return latestIssueDate;}
    public void setLatestIssueDate(String latestIssueDate) {this.latestIssueDate = latestIssueDate;}
}