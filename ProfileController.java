public class ProfileController {
    ConsoleReader reader = new ConsoleReader();

    public Profile createProfile() {
        StringValidator nameValidator = (value) -> !value.isEmpty();
        String name = reader.readString("Name", nameValidator);

        StringValidator lastValidator = (value) -> !value.isEmpty();
        String lastName = reader.readString("Last name", lastValidator);

        System.out.println("Birth date");

        IntegerValidator dayValidator = (value) -> value > 0 && value <= 31;
        int day = reader.readInt("Day (DD)", dayValidator);

        IntegerValidator monthValidator = (value) -> value > 0 && value <= 12;
        int month = reader.readInt("Mes (MM)", monthValidator);

        IntegerValidator yearValidator = (value) -> value > 999 && value <= 9999;
        int year = reader.readInt("Year (YYYY)", yearValidator);

        Date date = new Date(day, month, year);
        return new Profile(name, lastName, date);
    }

    public void editName(Object object) {
        StringValidator nameValidator = (value) -> !value.isEmpty();
        String name = reader.readString("Name", nameValidator);
        if (object instanceof Author) {
            ((Author) object).getProfile().setName(name);
        } else if (object instanceof Client) {
            ((Client) object).getProfile().setName(name);
        } else if (object instanceof Administrator) {
            ((Administrator) object).getProfile().setName(name);
        }
    }

    public void editLastName(Object object) {
        StringValidator lastValidator = (value) -> !value.isEmpty();
        String lastName = reader.readString("Last name", lastValidator);
        if (object instanceof Author) {
            ((Author) object).getProfile().setLastName(lastName);
        } else if (object instanceof Client) {
            ((Client) object).getProfile().setLastName(lastName);
        } else if (object instanceof Administrator) {
            ((Administrator) object).getProfile().setLastName(lastName);
        }
    }

    public void editBirth(Object object) {
        IntegerValidator dayValidator = (value) -> value > 0 && value <= 31;
        int day = reader.readInt("Day (DD)", dayValidator);

        IntegerValidator monthValidator = (value) -> value > 0 && value <= 12;
        int month = reader.readInt("Mes (MM)", monthValidator);

        IntegerValidator yearValidator = (value) -> value > 999 && value <= 9999;
        int year = reader.readInt("Year (YYYY)", yearValidator);

        Date date = new Date(day, month, year);
        if (object instanceof Author) {
            ((Author) object).getProfile().setBirthDate(date);
        } else if (object instanceof Client) {
            ((Client) object).getProfile().setBirthDate(date);
        } else if (object instanceof Administrator) {
            ((Administrator) object).getProfile().setBirthDate(date);
        }
    }
}
