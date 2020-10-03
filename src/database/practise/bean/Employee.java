package database.practise.bean;

import java.sql.Date;

public class Employee {

    private int id;
    private String name;
    private String nickname;
    private Date birthday;
    // for ui
    private String gender;
    // for db
    private int sex;
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
        sex = "男".equals(gender) ? 1 : 0;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
        gender = (sex == 1 ? "男" : "女");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", department=" + department.getName() +
                ", staffLevel=" + staffLevel.getName() +
                '}';
    }
}
