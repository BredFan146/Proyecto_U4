public class Profile {
    private String name;
    private String lastName;
    private Date birthDate;

    public Profile(String name, String lastName, Date date) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
