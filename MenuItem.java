public class MenuItem {
    private String text;
    private Controller controller;

    public MenuItem(String text, Controller controller) {
        this.text = text;
        this.controller = controller;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
