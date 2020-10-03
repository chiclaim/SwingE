package database.practise.bean;

public class Department {
    private int id;
    private String e_name;
    private Department parent;

    public Department(int id, String e_name) {
        this.id = id;
        this.e_name = e_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }
}
