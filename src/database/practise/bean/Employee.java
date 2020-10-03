package database.practise.bean;

import java.sql.Date;

public class Employee {

    private int id;
    private String name;
    private String nickname;
    private Date birthday;
    private String gender;
    private Department department;
    private Staff staffLevel;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Staff getStaffLevel() {
        return staffLevel;
    }

    public void setStaffLevel(Staff staffLevel) {
        this.staffLevel = staffLevel;
    }
}
