/**
 * Created by Dan on 6/16/16.
 */
public class Job {

    Integer jobId;
    String companyName;
    String location;
    String contactName;
    String contactNumber;
    String contactEmail;
    Boolean haveApplied;
    Integer rating;
    String comments;
    Integer userId;

    public Job() {
    }

    public Job(Integer jobId, String companyName, String location, String contactName, String contactNumber, String contactEmail, Boolean haveApplied, Integer rating, String comments, Integer userId) {
        this.jobId = jobId;
        this.companyName = companyName;
        this.location = location;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
        this.haveApplied = haveApplied;
        this.rating = rating;
        this.comments = comments;
        this.userId = userId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Boolean getHaveApplied() {
        return haveApplied;
    }

    public void setHaveApplied(Boolean haveApplied) {
        this.haveApplied = haveApplied;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getUserId() {
        return rating;
    }

    public void setUserId(Integer rating) {
        this.rating = rating;
    }
}
