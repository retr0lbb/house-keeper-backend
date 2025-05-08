package com.retr0lbb.housekeeper.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public class RolesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleID;

    private String name;

    public RolesModel() { }

    public RolesModel(long roleID, String name) {
        super();
        this.roleID = roleID;
        this.name = name;
    }

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Values{
        BASIC(2L),
        ADMIN(1L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }

}
