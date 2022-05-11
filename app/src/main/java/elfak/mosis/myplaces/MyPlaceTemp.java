package elfak.mosis.myplaces;

public class MyPlaceTemp {

    private String name;
    private String description;

    public MyPlaceTemp(String n, String d){
        this.name=n;
        this.description=d;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
