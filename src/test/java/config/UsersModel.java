package config;

public class UsersModel {
        private String firstName;
        private String lastName;
        private String empID;
        private String username;
        private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersModel(String firstName, String lastName,String empID, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.empID = empID;
        this.username = username;
        this.password = password;
    }

    public UsersModel(){

    }
}
