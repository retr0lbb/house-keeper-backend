package com.retr0lbb.housekeeper.entitys;
import jakarta.persistence.*;

@Entity
@Table(name="access_level")
public class AccessLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;

    @Column(unique = true)
    private String description;


    public AccessLevelEntity(Long id, Integer value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }
    public AccessLevelEntity(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
