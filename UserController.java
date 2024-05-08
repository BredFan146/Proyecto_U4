public class UserController implements ControllerCom {

    ConsoleReader reader = new ConsoleReader();
    ProfileController profCon = new ProfileController();
    Auxiliary aux = new Auxiliary();


    @Override
    public void create() {
        System.out.println("User information");
        Profile profile = profCon.createProfile();

        StringValidator userNameValidator = (value) -> {
            return !aux.checkPass(value);
        };
        String userName = reader.readString("UserName", userNameValidator);

        StringValidator passwordValidator = (value) -> {
            return value.length() >= 6 && value.length() <= 8;
        };
        String password = reader.readString("Password (6-8 characters)", passwordValidator);

    }


    @Override
    public void delete() {
        StringValidator nameValidator = (value) -> !value.isEmpty();
        String name = reader.readString("User name", nameValidator);

        StringValidator lastValidator = (value) -> !value.isEmpty();
        String lastName = reader.readString("User last name", lastValidator);

        UserRepository.users.remove(this.getUser(name, lastName));
    }

    public void addToArray(User user) {
        UserRepository.users.add(user);
    }


    @Override
    public void edit() {
    }

    @Override
    public void getArray() {
        System.out.printf("%30s %n----------------------------------------------%n", "Administrators");
        System.out.printf("| %-12s | %-12s | %-12s |%n", "Name", "Lastname", "Birth date");
        for (User user: UserRepository.users){
                System.out.printf("| %-12s | %-12s | %-12s |%n" +
                        "----------------------------------------------%n", user.getProfile().getName(), user.getProfile().getLastName(), user.getProfile().getBirthDate().dateString());
        }
    }


    public User getUser(String name, String lastName) {
        for (User user : UserRepository.users) {
            if (user.getProfile().getName().equals(name) && user.getProfile().getLastName().equals(lastName)){
                return user;
            }
        }
        return null;
    }
}
