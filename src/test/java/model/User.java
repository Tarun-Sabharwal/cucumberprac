package model;

public class User {
    private Integer id;
    private String name;
    private Boolean access;

    public Integer get_id()
    {
        return id;
    }

    public void set_id(Integer id)
    {
        this.id = id;
    }

    public String get_name()
    {
        return name;
    }

    public void set_name(String name)
    {
        this.name = name;
    }

    public Boolean get_access()
    {
        return access;
    }

    public void set_access(Boolean access)
    {
        this.access = access;
    }


}
