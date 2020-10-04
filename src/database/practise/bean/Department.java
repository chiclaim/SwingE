package database.practise.bean;

public class Department {
    private int id;
    private String name;
    private Department parent;


    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(String name) {
        this(0, name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }
}
